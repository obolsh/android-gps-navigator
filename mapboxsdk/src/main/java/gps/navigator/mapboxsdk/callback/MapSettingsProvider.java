package gps.navigator.mapboxsdk.callback;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import gps.map.navigator.model.interfaces.MapSetting;
import gps.navigator.mapboxsdk.StyleProvider;
import gps.navigator.mapboxsdk.interfaces.MapSdkProviderListener;

public class MapSettingsProvider implements MapSdkProviderListener {

    private MapSetting setting;

    public MapSettingsProvider(MapSetting setting) {
        this.setting = setting;
    }

    @Override
    public void onMapSdkProvided(MapboxMap mapboxMap) {
        mapboxMap.setStyle(new StyleProvider().getStyle(setting), null);
    }
}
