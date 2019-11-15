package gps.navigator.mapboxsdk;

import com.mapbox.mapboxsdk.maps.MapView;

import gps.map.navigator.model.InstanceCache;

public class MapViewInstance extends InstanceCache<MapView> {

    public MapViewInstance(MapView instance) {
        super(instance);
    }
}
