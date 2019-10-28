package gps.map.navigator.view.viewmodel.callback;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;

public class BuildRouteCallback implements IPlaceHistoryListener {

    private BuildRouteFragment fragment;

    public BuildRouteCallback(BuildRouteFragment recyclerView) {
        this.fragment = recyclerView;
    }

    @Override
    public void onHistoryPlacesFound(List<IMapPlace> placeList) {
        if (fragment != null) {
            fragment.setHistoryPlaces(placeList);
        }
    }

    @Override
    public void onHistoryPlacesError(Exception reason) {

    }
}
