package gps.navigator.mapboxsdk;

import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.MapType;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSetting;

public class StyleProvider {

    public String getActiveStyle(Cache cache) {
        return cache != null ? getStyle(cache.getMapSettings()) : Style.MAPBOX_STREETS;
    }

    public String getStyle(MapSetting setting) {
        if (setting == null) {
            return Style.MAPBOX_STREETS;
        }
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
