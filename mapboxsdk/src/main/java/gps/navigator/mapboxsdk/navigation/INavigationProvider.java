package gps.navigator.mapboxsdk.navigation;

import androidx.annotation.Nullable;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;

import gps.navigator.mapboxsdk.interfaces.RouteReadyListener;

public interface INavigationProvider {

    void buildRoute(@Nullable Point origin, @Nullable Point destination, @Nullable RouteReadyListener listener);

    void drawRoute(@Nullable DirectionsRoute route);
}
