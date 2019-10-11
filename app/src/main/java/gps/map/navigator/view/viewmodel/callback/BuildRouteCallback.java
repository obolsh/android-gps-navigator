package gps.map.navigator.view.viewmodel.callback;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;

public class BuildRouteCallback implements IPlaceHistoryListener {
    @Override
    public void onHistoryPlacesFound(List<IMapPlace> placeList) {

    }

    @Override
    public void onHistoryPlacesError(Exception reason) {

    }
}
