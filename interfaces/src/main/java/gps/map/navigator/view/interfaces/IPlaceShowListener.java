package gps.map.navigator.view.interfaces;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface IPlaceShowListener {

    void onPlaceShown(IMapPlace place);

    void onPlaceShowFailed(Exception reason);
}
