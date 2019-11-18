package gps.navigator.mapboxsdk.callback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import gps.navigator.mapboxsdk.interfaces.MapSdkProviderListener;

public abstract class MapLocationProvider implements MapSdkProviderListener {

    @Override
    public void onMapSdkProvided(@Nullable MapboxMap mapboxMap) {
        if (mapboxMap != null) {
            mapboxMap.setStyle(getMapStyle(), new StyleLoader(mapboxMap, this));
        }
    }

    private static class StyleLoader implements Style.OnStyleLoaded {
        private MapboxMap mapboxMap;
        private MapLocationProvider mapLocationProvider;

        private StyleLoader(MapboxMap mapboxMap, MapLocationProvider mapLocationProvider) {
            this.mapboxMap = mapboxMap;
            this.mapLocationProvider = mapLocationProvider;
        }

        @Override
        public void onStyleLoaded(@NonNull Style style) {
            if (mapLocationProvider != null && mapboxMap != null) {
                mapLocationProvider.openMap(mapboxMap);
            }
        }
    }

    public abstract void openMap(MapboxMap mapboxMap);

    public abstract String getMapStyle();
}
