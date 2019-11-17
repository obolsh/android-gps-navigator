package gps.navigator.mapboxsdk.callback;

import androidx.annotation.NonNull;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.navigator.mapboxsdk.MapSdkInstance;
import gps.navigator.mapboxsdk.MapSdkProvider;
import gps.navigator.mapboxsdk.interfaces.MapSdkProviderListener;

public class MapSdkProviderListenerImpl implements MapSdkProviderListener {
    private Cache cache;
    private MapSdk mapSdk;

    public MapSdkProviderListenerImpl(Cache cache, MapSdk mapSdk) {
        this.cache = cache;
        this.mapSdk = mapSdk;
    }

    @Override
    public void onMapSdkProvided(MapboxMap mapboxMap) {
        MapSdkProvider provider = MapSdkProvider.getInstance();
        MapSdkInstance instance = provider.getMapSdkInstance();
        if (instance == null) {
            instance = new MapSdkInstance(mapboxMap);
            provider.setMapSdkInstance(instance);
        }
        if (cache != null) {
            instance.setStyle(cache.getMapSettings(), new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    if (mapSdk != null) {
                        mapSdk.showMap();
                    }
                }
            });
        }
    }
}
