package gps.map.navigator.view.viewmodel.callback;

import androidx.annotation.NonNull;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceShowListener;

public class FindAndShowCallback implements IPlaceShowListener {
    @Override
    public void onPlaceShown(@NonNull IMapPlace place) {

    }

    @Override
    public void onPlaceShowFailed(@NonNull Exception reason) {

    }
}
