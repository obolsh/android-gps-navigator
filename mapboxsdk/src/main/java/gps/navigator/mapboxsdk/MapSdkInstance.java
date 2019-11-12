package gps.navigator.mapboxsdk;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.InstanceCache;
import gps.map.navigator.model.MapType;
import gps.map.navigator.model.interfaces.MapSetting;

public class MapSdkInstance extends InstanceCache<MapboxMap> {

    public MapSdkInstance(MapboxMap instance) {
        super(instance);
    }

    public void setStyle(MapSetting setting) {
        MapboxMap map = getInstance();
        if (map != null) {
            map.setStyle(getStyle(setting));
        }
    }

    private String getStyle(MapSetting setting) {
        switch (setting.getMapType()) {
            case MapType.NORMAL_DAY:
                return Style.MAPBOX_STREETS;

            case MapType.NORMAL_NIGHT:
            case MapType.SATELLITE_NIGHT:
            case MapType.NORMAL_TRAFFIC_NIGHT:
            case MapType.SATELLITE_TRAFFIC_NIGHT:
                return Style.TRAFFIC_NIGHT;

            case MapType.SATELLITE_DAY:
                return Style.SATELLITE;

            case MapType.NORMAL_TRAFFIC_DAY:
            case MapType.SATELLITE_TRAFFIC_DAY:
                return Style.TRAFFIC_DAY;

            default:
                return Style.MAPBOX_STREETS;
        }
    }
}
