package gps.navigator.mapboxsdk;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import gps.map.navigator.model.InstanceCache;
import gps.map.navigator.model.interfaces.MapSetting;

public class MapSdkInstance extends InstanceCache<MapboxMap> {

    public MapSdkInstance(MapboxMap instance) {
        super(instance);
    }

    public void setStyle(MapSetting setting) {
        MapboxMap map = getInstance();
        if (map != null) {
            map.setStyle(new StyleProvider().getStyle(setting));
        }
    }
}
