package gps.navigator.mapboxsdk;

import com.mapbox.api.directions.v5.models.DirectionsRoute;

import gps.map.navigator.model.InstanceCache;
@Deprecated
public class MapRouteInstance extends InstanceCache<DirectionsRoute> {
    public MapRouteInstance(DirectionsRoute instance) {
        super(instance);
    }
}
