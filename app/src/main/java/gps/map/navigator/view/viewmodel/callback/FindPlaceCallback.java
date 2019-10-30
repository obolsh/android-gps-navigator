package gps.map.navigator.view.viewmodel.callback;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.interfaces.IPlaceListener;

public class FindPlaceCallback implements IPlaceListener {
    private PlaceProxyListener listener;

    public FindPlaceCallback(PlaceProxyListener listener) {
        this.listener = listener;
    }

    @Override
    public void onPlaceLocated(IMapPlace place) {
        if (listener != null) {
            listener.onPlaceLocated(place);
        }
    }

    @Override
    public void onPlaceLocationFailed(Exception reason) {

    }
}
