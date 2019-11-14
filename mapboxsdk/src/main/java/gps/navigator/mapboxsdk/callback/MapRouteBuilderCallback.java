package gps.navigator.mapboxsdk.callback;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;

import gps.navigator.mapboxsdk.interfaces.RouteReadyListener;
import gps.navigator.mapboxsdk.navigation.MapRouteProvider;
import gps.navigator.mapboxsdk.navigation.NavigationRouteStrategy;

public class MapRouteBuilderCallback implements OnMapReadyCallback {

    private Cache cache;
    private Context context;
    private Logger logger;

    public MapRouteBuilderCallback(Context context, Cache cache, Logger logger) {
        this.cache = cache;
        this.context = context;
        this.logger = logger;
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        NavigationRouteStrategy.getInstance()
                .setStrategy(new MapRouteProvider(context, cache, mapboxMap))
                .buildRoute(getOrigin(), getDestination(), new RouteReadyListener() {
                    @Override
                    public void onRouteReady(DirectionsRoute route) {
                        NavigationRouteStrategy.getInstance().drawRoute(route);
                    }

                    @Override
                    public void onBuildFailed(Exception reason) {
                        logger.error(reason);
                    }
                });
    }

    private Point getOrigin() {
        IMapPlace place = cache.getLastRoute().getOrigin();
        return Point.fromLngLat(place.getLongitude(), place.getLatitude());
    }

    private Point getDestination() {
        IMapPlace place = cache.getLastRoute().getDestination();
        return Point.fromLngLat(place.getLongitude(), place.getLatitude());
    }
}
