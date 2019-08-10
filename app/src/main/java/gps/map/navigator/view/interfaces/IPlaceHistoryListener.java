package gps.map.navigator.view.interfaces;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface IPlaceHistoryListener {

    void onHistoryPlacesFound(List<IMapPlace> placeList);

    void onHistoryPlacesError(Exception reason);
}
