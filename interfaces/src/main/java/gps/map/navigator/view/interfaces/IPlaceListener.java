package gps.map.navigator.view.interfaces;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface IPlaceListener {

    void onPlaceLocated(IMapPlace place);

    void onPlacesLocated(List<IMapPlace> place);

    void onPlaceLocationFailed(Exception reason);
}
