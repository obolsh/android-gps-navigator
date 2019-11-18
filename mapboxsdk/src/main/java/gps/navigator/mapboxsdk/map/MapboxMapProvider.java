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

import org.jetbrains.annotations.Nullable;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.navigator.mapboxsdk.callback.StyleLoadedCallback;

public class MapboxMapProvider implements IMapProvider {
    @Nullable
    private Context context;
    @Nullable
    private Cache cache;
    @Nullable
    private MapboxMap mapboxMap;

    public MapboxMapProvider(@Nullable Context context, @Nullable Cache cache, @Nullable MapboxMap mapboxMap) {
        this.context = context;
        this.cache = cache;
        this.mapboxMap = mapboxMap;
    }

    @Override
    public void showDeviceLocation(@Nullable IPlaceListener placeListener) {
        if (mapboxMap != null) {
            mapboxMap.getStyle(new StyleLoadedCallback(context, mapboxMap, placeListener));
        } else if (placeListener != null) {
            placeListener.onPlaceLocationFailed(new Exception("No map cache"));
        }
    }

    @Override
    public void showInitialLocation(@Nullable final IPlaceListener placeListener) {
        if (mapboxMap != null) {
            final CameraPosition location = getPosition();
            if (location != null) {
                mapboxMap.setCameraPosition(location);
                mapboxMap.getStyle(new Style.OnStyleLoaded() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        if (context != null && PermissionsManager.areLocationPermissionsGranted(context)) {
                            try {
                                LocationComponent component = mapboxMap.getLocationComponent();
                                component.activateLocationComponent(
                                        LocationComponentActivationOptions.builder(context, style)
                                                .useDefaultLocationEngine(false)
                                                .build());
                                if (locationEnabled()) {
                                    component.setLocationComponentEnabled(true);
                                    component.setCameraMode(CameraMode.TRACKING);
                                    component.setRenderMode(RenderMode.COMPASS);
                                    component.forceLocationUpdate(buildLastLocation());
                                } else {
                                    component.setLocationComponentEnabled(false);
                                }
                            } catch (Throwable t) {
                                if (placeListener != null) {
                                    placeListener.onPlaceLocationFailed(new Exception(t));
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private boolean locationEnabled() {
        if (context == null) {
            return false;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            return lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            return false;
        }
    }

    private Location buildLastLocation() {
        if (cache == null) {
            return null;
        }
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
        if (cache == null) {
            return null;
        }
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
