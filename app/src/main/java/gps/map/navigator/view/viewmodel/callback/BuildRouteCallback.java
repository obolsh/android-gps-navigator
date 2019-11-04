package gps.map.navigator.view.viewmodel.callback;

import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;

public class BuildRouteCallback implements IPlaceHistoryListener {

    @Inject
    ICachedPlaceCallback fragment;

    @Inject
    BuildRouteCallback() {
    }

    @Override
    public void onHistoryPlacesFound(List<IMapPlace> placeList) {
        fragment.setHistoryPlaces(placeList);
    }

    @Override
    public void onHistoryPlacesError(Exception reason) {

    }
}
