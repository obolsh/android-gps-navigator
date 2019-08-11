package gps.map.navigator.view.interfaces;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface IPlaceListener {

    void onPlaceLocated(IMapPlace place);

    void onPlaceLocationFailed(Exception reason);
}
