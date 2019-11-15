package gps.navigator.mapboxsdk.interfaces;

import com.mapbox.api.directions.v5.models.DirectionsRoute;

public interface RouteReadyListener {

    void onRouteReady(DirectionsRoute route);

    void onBuildFailed(Exception reason);
}
