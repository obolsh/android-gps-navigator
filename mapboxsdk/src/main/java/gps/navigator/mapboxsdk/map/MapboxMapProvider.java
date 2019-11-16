package gps.navigator.mapboxsdk.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.navigator.mapboxsdk.MapSdkInstance;
import gps.navigator.mapboxsdk.MapSdkProvider;
import gps.navigator.mapboxsdk.callback.StyleLoadedCallback;

public class MapboxMapProvider implements IMapProvider {
    private Context context;
    private Cache cache;

    public MapboxMapProvider(Context context, Cache cache) {
        this.context = context;
        this.cache = cache;
    }

    @Override
    public void showDeviceLocation(IPlaceListener placeListener) {
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
    public void showInitialLocation(IPlaceListener placeListener) {
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
                        if (PermissionsManager.areLocationPermissionsGranted(context) && locationEnabled()) {
                            component.setLocationComponentEnabled(true);
                        } else {
                            component.setLocationComponentEnabled(false);
                        }
                        component.setCameraMode(CameraMode.TRACKING);
                        component.setRenderMode(RenderMode.COMPASS);
                        component.forceLocationUpdate(buildLastLocation());
                    }
                });
            }
        }
    }

    private boolean locationEnabled() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            return lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            return false;
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
