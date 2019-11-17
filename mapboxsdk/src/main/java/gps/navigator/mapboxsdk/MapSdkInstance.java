package gps.navigator.mapboxsdk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.InstanceCache;
import gps.map.navigator.model.interfaces.MapSetting;

public class MapSdkInstance extends InstanceCache<MapboxMap> {

    public MapSdkInstance(MapboxMap instance) {
        super(instance);
    }

    public void setStyle(MapSetting setting, @Nullable final Style.OnStyleLoaded listener) {
        MapboxMap map = getInstance();
        if (map != null) {
            map.setStyle(new StyleProvider().getStyle(setting), new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    if (listener != null) {
                        listener.onStyleLoaded(style);
                    }
                }
            });
        }
    }
}
