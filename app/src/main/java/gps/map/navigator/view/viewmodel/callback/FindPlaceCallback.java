package gps.map.navigator.view.viewmodel.callback;

import androidx.annotation.NonNull;

import java.util.List;

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
    public void onPlaceLocated(@NonNull IMapPlace place) {
        listener.onPlaceLocated(place);
    }

    @Override
    public void onPlacesLocated(List<IMapPlace> place) {
        listener.onPlacesLocated(place);
    }

    @Override
    public void onPlaceLocationFailed(@NonNull Exception reason) {

    }
}
