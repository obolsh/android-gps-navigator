package gps.map.navigator.view.viewmodel.recyclerview.listener;

import android.view.View;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

public class PickedListener implements View.OnClickListener {
    private IMapPlace mapPlace;
    private IPlacePickerCallback placePickerCallback;


    public PickedListener(IMapPlace mapPlace, IPlacePickerCallback placePickerCallback) {
        this.mapPlace = mapPlace;
        this.placePickerCallback = placePickerCallback;
    }

    @Override
    public void onClick(View v) {
        if (placePickerCallback != null && mapPlace != null) {
            placePickerCallback.setNewPickedPlace(mapPlace);
        }
    }
}
