package gps.navigator.mapboxsdk.callback;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    @Nullable
    private Cache cache;
    @Nullable
    private Context context;
    @Nullable
    private Logger logger;
    @Nullable
    private MapView mapView;

    public MapRouteBuilderCallback(@Nullable Context context, @Nullable Cache cache, @Nullable Logger logger, @Nullable MapView mapView) {
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
                        .setStrategy(new NavigationRouteProvider(context, mapboxMap, mapView, style))
                        .buildRoute(getOrigin(), getDestination(), new RouteReadyListener() {
                            @Override
                            public void onRouteReady(DirectionsRoute route) {
                                NavigationRouteStrategy.getInstance().drawRoute(route);
                            }

                            @Override
                            public void onBuildFailed(Exception reason) {
                                if (logger != null) {
                                    logger.error(reason);
                                }
                            }
                        });
            }
        });
    }

    private Point getOrigin() {
        if (cache == null || cache.getLastRoute() == null) {
            return null;
        }
        IMapPlace place = cache.getLastRoute().getOrigin();
        return Point.fromLngLat(place.getLongitude(), place.getLatitude());
    }

    private Point getDestination() {
        if (cache == null || cache.getLastRoute() == null) {
            return null;
        }
        IMapPlace place = cache.getLastRoute().getDestination();
        return Point.fromLngLat(place.getLongitude(), place.getLatitude());
    }
}
