package gps.navigator.mapboxsdk.map;

import gps.map.navigator.view.interfaces.IPlaceListener;

public class MapProviderStategy implements IMapProvider {

    private static final MapProviderStategy instance = new MapProviderStategy();
    private IMapProvider provider;

    private MapProviderStategy() {
    }

    public static MapProviderStategy getInstance() {
        return instance;
    }

    public MapProviderStategy setStrategy(IMapProvider provider) {
        this.provider = provider;
        return this;
    }

    @Override
    public void showDeviceLocation(IPlaceListener placeListener) {
        if (provider != null) {
            provider.showDeviceLocation(placeListener);
        }
    }

    @Override
    public void showInitialLocation(IPlaceListener placeListener) {
        if (provider != null) {
            provider.showInitialLocation(placeListener);
        }
    }
}
