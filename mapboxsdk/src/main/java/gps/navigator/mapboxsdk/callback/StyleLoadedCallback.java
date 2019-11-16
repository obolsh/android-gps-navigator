package gps.navigator.mapboxsdk.callback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.impl.MyLocation;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.navigator.mapboxsdk.geocode.GeocodeStrategy;
import gps.navigator.mapboxsdk.geocode.ReverseGeocodeListener;
import gps.navigator.mapboxsdk.geocode.impl.LocationIqGeocode;

import static android.os.Looper.getMainLooper;

public class StyleLoadedCallback implements Style.OnStyleLoaded {

    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    private MapboxMap map;
    private IPlaceListener placeListener;
    private Context context;
    private LocationEngine locationEngine;
    private LocationCallback callback;

    public StyleLoadedCallback(Context context, MapboxMap map, IPlaceListener placeListener) {
        this.map = map;
        this.placeListener = placeListener;
        this.context = context;
        callback = new LocationCallback(map, placeListener);
    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {
        enableLocationComponent(style);
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(Style style) {
        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            if (map != null) {
                LocationComponent component = map.getLocationComponent();
                component.activateLocationComponent(
                        LocationComponentActivationOptions.builder(context, style)
                                .useDefaultLocationEngine(false)
                                .build());

                component.setLocationComponentEnabled(locationStatus());
                component.setCameraMode(CameraMode.TRACKING);
                component.setRenderMode(RenderMode.COMPASS);
                component.zoomWhileTracking(15d, 2000L);
                initLocationEngine();
            } else {
                placeListener.onPlaceLocationFailed(new Exception("No map sdk"));
            }
        } else {
            placeListener.onPlaceLocationFailed(new Exception("No permission"));
        }
    }

    private boolean locationStatus() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            return lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            return false;
        }
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(context);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    private static class LocationCallback implements LocationEngineCallback<LocationEngineResult> {
        private MapboxMap map;
        private IPlaceListener placeListener;

        private LocationCallback(MapboxMap map, IPlaceListener placeListener) {
            this.map = map;
            this.placeListener = placeListener;
        }

        @Override
        public void onSuccess(LocationEngineResult result) {
            final Location location = result.getLastLocation();
            if (map != null && location != null) {
                map.getLocationComponent().forceLocationUpdate(location);
                GeocodeStrategy.getInstance()
                        .setStategy(new LocationIqGeocode())
                        .getPlaceByLocation(buildMyLocation(location), new ReverseGeocodeListener() {
                            @Override
                            public void onPlaceDetected(IMapPlace mapPlace) {
                                placeListener.onPlaceLocated(mapPlace);
                            }

                            @Override
                            public void onPlaceDetectFailed(Exception reason) {

                            }
                        });
            }
        }

        private IMapPlace buildMyLocation(Location location) {
            IMapPlace place = new MyLocation();
            place.setLongitude(location.getLongitude());
            place.setLatitude(location.getLatitude());
            return place;
        }


        @Override
        public void onFailure(@NonNull Exception exception) {
            placeListener.onPlaceLocationFailed(exception);
        }
    }
}