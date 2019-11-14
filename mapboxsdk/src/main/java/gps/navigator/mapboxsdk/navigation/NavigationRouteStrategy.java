package gps.navigator.mapboxsdk.navigation;

import androidx.annotation.Nullable;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

import gps.navigator.mapboxsdk.interfaces.RouteReadyListener;

public class NavigationRouteStrategy implements INavigationProvider {

    private static final NavigationRouteStrategy instance = new NavigationRouteStrategy();
    private INavigationProvider provider;

    private NavigationRouteStrategy() {
    }

    public static NavigationRouteStrategy getInstance() {
        return instance;
    }

    public NavigationRouteStrategy setStrategy(@Nullable INavigationProvider provider) {
        this.provider = provider;
        return this;
    }

    @Override
    public void buildRoute(@Nullable Point origin, @Nullable Point destination, @Nullable RouteReadyListener listener) {
        if (provider != null) {
            provider.buildRoute(origin, destination, listener);
        }
    }

    @Override
    public void drawRoute(@Nullable DirectionsRoute route) {
        if (provider != null) {
            provider.drawRoute(route);
        }
    }
}
