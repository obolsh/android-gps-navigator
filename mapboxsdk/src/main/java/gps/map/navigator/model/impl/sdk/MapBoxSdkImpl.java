package gps.map.navigator.model.impl.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import javax.inject.Inject;
import javax.inject.Named;

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

public class MapBoxSdkImpl implements MapSdk {
    @Inject
    @Named("application_context")
    Context context;
    @Inject
    Cache cache;

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
    public void findPlace(String query, IPlaceListener placeListener) {

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
            final Location location = buildLastLocation();
            if (map != null && location != null) {
                map.setMinZoomPreference(15d);
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
                        component.cancelZoomWhileTrackingAnimation();

                        component.forceLocationUpdate(location);

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
}
