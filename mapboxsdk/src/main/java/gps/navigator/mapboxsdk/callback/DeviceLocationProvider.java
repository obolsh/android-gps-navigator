package gps.navigator.mapboxsdk.callback;

import android.content.Context;

import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.navigator.mapboxsdk.StyleProvider;
import gps.navigator.mapboxsdk.map.MapProviderStategy;
import gps.navigator.mapboxsdk.map.MapboxMapProvider;

public class DeviceLocationProvider extends MapLocationProvider {
    @Nullable
    private Cache cache;
    @Nullable
    private Context context;
    @Nullable
    private IPlaceListener listener;

    public DeviceLocationProvider(@Nullable Context context, @Nullable Cache cache, @Nullable IPlaceListener listener) {
        this.cache = cache;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void openMap(MapboxMap mapboxMap) {
        MapProviderStategy.getInstance()
                .setStrategy(new MapboxMapProvider(context, cache, mapboxMap))
                .showDeviceLocation(listener);
    }

    @Override
    public String getMapStyle() {
        return cache != null ? new StyleProvider().getStyle(cache.getMapSettings()) : Style.MAPBOX_STREETS;
    }
}
