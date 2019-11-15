package gps.navigator.mapboxsdk.map;

import gps.map.navigator.view.interfaces.IPlaceListener;

public interface IMapProvider {

    void showDeviceLocation(IPlaceListener placeListener);

    void showInitialLocation(IPlaceListener placeListener);
}
