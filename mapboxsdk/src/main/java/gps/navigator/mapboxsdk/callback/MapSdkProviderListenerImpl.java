package gps.navigator.mapboxsdk.callback;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import gps.map.navigator.model.interfaces.Cache;
import gps.navigator.mapboxsdk.MapSdkInstance;
import gps.navigator.mapboxsdk.MapSdkProvider;
import gps.navigator.mapboxsdk.interfaces.MapSdkProviderListener;

public class MapSdkProviderListenerImpl implements MapSdkProviderListener {
    private Cache cache;

    public MapSdkProviderListenerImpl(Cache cache) {
        this.cache = cache;
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
            instance.setStyle(cache.getMapSettings());
        }
    }
}
