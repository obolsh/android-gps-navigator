package gps.map.navigator.model.impl.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.impl.FoundedPlace;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;
import gps.navigator.mapboxsdk.MapSdkInstance;
import gps.navigator.mapboxsdk.MapSdkProvider;
import gps.navigator.mapboxsdk.R;
import gps.navigator.mapboxsdk.callback.StyleLoadedCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapBoxSdkImpl implements MapSdk {
    @Inject
    @Named("application_context")
    Context context;
    @Inject
    Cache cache;
    @Inject
    Logger logger;

    @Inject
    public MapBoxSdkImpl() {
    }

    @Override
    public void initialize(Context context, Bundle bundle) {
        Mapbox.getInstance(context.getApplicationContext(), context.getString(R.string.mapbox_access_token));
    }

    @Override
    public void showMeOnMap(IPlaceListener placeListener) {
        MapSdkProvider provider = MapSdkProvider.getInstance();
        MapSdkInstance mapboxMap = provider.getMapSdkInstance();
        if (mapboxMap != null) {
            MapboxMap map = mapboxMap.getInstance();
            if (map != null) {
                map.getStyle(new StyleLoadedCallback(context, map, placeListener));
            } else {
                placeListener.onPlaceLocationFailed(new Exception("No map"));
            }
        } else {
            placeListener.onPlaceLocationFailed(new Exception("No map cache"));
        }

    }

    @Override
    public void setMapSettings(MapSetting mapSettings) {
        MapSdkInstance map = MapSdkProvider.getInstance().getMapSdkInstance();
        if (map != null) {
            map.setStyle(mapSettings);
        }
    }

    @Override
    public void showPlace(IMapPlace place, IPlaceShowListener placeShowListener) {

    }

    @Override
    public void showRoute(IRoute route, IRouteReadyListener routeListener) {

    }

    @Override
    public void findPlace(String query, final IPlaceListener placeListener) {
        MapboxGeocoding geocoding = MapboxGeocoding.builder()
                .accessToken(context.getString(R.string.mapbox_access_token))
                .query(query)
//                .geocodingTypes(GeocodingCriteria.TYPE_ADDRESS, GeocodingCriteria.TYPE_COUNTRY, GeocodingCriteria.TYPE_PLACE)
//                .reverseMode(GeocodingCriteria.REVERSE_MODE_DISTANCE)
                .limit(3)
                .build();
        logger.debug("Searching for: " + query);
        geocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                logger.debug("Geocoding response");
                GeocodingResponse body = response.body();
                if (body != null) {
                    List<CarmenFeature> results = body.features();

                    if (results.size() > 0) {
                        List<IMapPlace> places = new ArrayList<>();
                        CarmenFeature carmenFeature;
                        for (int i = 0; i < results.size(); i++) {
                            carmenFeature = results.get(i);
                            places.add(new FoundedPlace(carmenFeature));
                            logger.debug("Founded place: " + carmenFeature.toString());
                        }
                        placeListener.onPlacesLocated(places);
                    } else {
                        placeListener.onPlaceLocationFailed(new Exception("Not found"));
                    }
                } else {
                    placeListener.onPlaceLocationFailed(new Exception("Not found"));
                }

            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                logger.error(throwable);
                placeListener.onPlaceLocationFailed(new Exception(throwable));
            }
        });
    }

    @Override
    public IMapPlace getMyLocation() {
        return null;
    }

    @Override
    public IMapPlace getLastPickedPlace() {
        return null;
    }

    @Override
    public void navigate(IRoute route, IRouteListener routeListener) {

    }

    @Override
    public void showMap() {
        MapSdkProvider provider = MapSdkProvider.getInstance();
        MapSdkInstance mapboxMap = provider.getMapSdkInstance();
        if (mapboxMap != null) {
            final MapboxMap map = mapboxMap.getInstance();
            final CameraPosition location = getPosition();
            if (map != null && location != null) {
                map.setCameraPosition(location);
                map.getStyle(new Style.OnStyleLoaded() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        LocationComponent component = map.getLocationComponent();
                        component.activateLocationComponent(
                                LocationComponentActivationOptions.builder(context, style)
                                        .useDefaultLocationEngine(false)
                                        .build());

                        component.setLocationComponentEnabled(true);
                        component.setCameraMode(CameraMode.TRACKING);
                        component.setRenderMode(RenderMode.COMPASS);
                        component.forceLocationUpdate(buildLastLocation());
                    }
                });
            }
        }
    }

    private Location buildLastLocation() {
        Location location = new Location("");
        IMapPlace place = cache.getLastPlace();
        if (place == null) {
            place = cache.getMyLocation();
        }
        if (place != null) {
            location.setLatitude(place.getLatitude());
            location.setLongitude(place.getLongitude());
        } else {
            return null;
        }
        return location;
    }

    private CameraPosition getPosition() {
        IMapPlace place = cache.getLastPlace();
        if (place == null) {
            place = cache.getMyLocation();
        }
        if (place != null) {
            LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
            return new CameraPosition.Builder().target(latLng).zoom(15d).build();
        } else {
            return null;
        }
    }
}
