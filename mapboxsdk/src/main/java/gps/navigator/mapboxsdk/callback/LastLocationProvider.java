package gps.navigator.mapboxsdk.callback;

import android.content.Context;

import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.interfaces.Cache;
import gps.navigator.mapboxsdk.StyleProvider;
import gps.navigator.mapboxsdk.map.MapProviderStategy;
import gps.navigator.mapboxsdk.map.MapboxMapProvider;

public class LastLocationProvider extends MapLocationProvider {
    @Nullable
    private Cache cache;
    @Nullable
    private Context context;

    public LastLocationProvider(@Nullable Context context, @Nullable Cache cache) {
        this.cache = cache;
        this.context = context;
    }

    @Override
    public void openMap(MapboxMap mapboxMap) {
        MapProviderStategy.getInstance()
                .setStrategy(new MapboxMapProvider(context, cache, mapboxMap))
                .showInitialLocation(null);
    }

    @Override
    public String getMapStyle() {
        return cache != null ? new StyleProvider().getActiveStyle(cache) : Style.MAPBOX_STREETS;
    }
}
