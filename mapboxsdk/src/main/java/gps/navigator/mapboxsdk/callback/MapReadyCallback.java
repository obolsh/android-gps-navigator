package gps.navigator.mapboxsdk.callback;

import androidx.annotation.NonNull;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.MapType;
import gps.map.navigator.model.interfaces.MapSetting;

public class MapReadyCallback implements OnMapReadyCallback {
    private MapSetting mapSetting;

    public MapReadyCallback(MapSetting mapSetting) {
        this.mapSetting = mapSetting;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(getStyle(), new MapStyleLoadedCallback());
    }

    private String getStyle() {
        switch (mapSetting.getMapType()) {
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
