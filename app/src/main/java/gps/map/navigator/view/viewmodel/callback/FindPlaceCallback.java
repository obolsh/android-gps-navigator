package gps.map.navigator.view.viewmodel.callback;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.interfaces.IPlaceListener;

public class FindPlaceCallback implements IPlaceListener {
    @Inject
    PlaceProxyListener listener;

    @Inject
    FindPlaceCallback() {
    }

    @Override
    public void onPlaceLocated(IMapPlace place) {
        listener.onPlaceLocated(place);
    }

    @Override
    public void onPlaceLocationFailed(Exception reason) {

    }
}
