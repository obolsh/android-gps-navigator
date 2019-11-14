package gps.navigator.mapboxsdk.callback;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;

import gps.navigator.mapboxsdk.StyleProvider;
import gps.navigator.mapboxsdk.interfaces.RouteReadyListener;
import gps.navigator.mapboxsdk.navigation.NavigationRouteProvider;
import gps.navigator.mapboxsdk.navigation.NavigationRouteStrategy;

public class MapRouteBuilderCallback implements OnMapReadyCallback {

    private Cache cache;
    private Context context;
    private Logger logger;
    private MapView mapView;

    public MapRouteBuilderCallback(Context context, Cache cache, Logger logger, MapView mapView) {
        this.cache = cache;
        this.context = context;
        this.logger = logger;
        this.mapView = mapView;
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        mapboxMap.setStyle(new StyleProvider().getActiveStyle(cache), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                NavigationRouteStrategy.getInstance()
//                .setStrategy(new MapRouteProvider(context, cache, mapboxMap))
                        .setStrategy(new NavigationRouteProvider(context, mapboxMap, mapView, style))
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
