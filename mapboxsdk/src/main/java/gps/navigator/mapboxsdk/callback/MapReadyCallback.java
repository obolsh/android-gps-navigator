package gps.navigator.mapboxsdk.callback;

import androidx.annotation.NonNull;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import gps.navigator.mapboxsdk.interfaces.MapSdkProviderListener;

public class MapReadyCallback implements OnMapReadyCallback {
    private MapSdkProviderListener listener;

    public MapReadyCallback(MapSdkProviderListener listener) {
        this.listener = listener;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        if (listener != null) {
            listener.onMapSdkProvided(mapboxMap);
        }
        listener = null;
    }
}
