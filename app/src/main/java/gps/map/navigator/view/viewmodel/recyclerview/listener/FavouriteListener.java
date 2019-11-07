package gps.map.navigator.view.viewmodel.recyclerview.listener;

import android.view.View;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

public class FavouriteListener implements View.OnClickListener {
    private IMapPlace mapPlace;
    private IPlacePickerCallback placePickerCallback;

    public FavouriteListener(IMapPlace mapPlace, IPlacePickerCallback placePickerCallback) {
        this.mapPlace = mapPlace;
        this.placePickerCallback = placePickerCallback;
    }

    @Override
    public void onClick(View v) {
        if (notNull()) {
            if (mapPlace.isFavourite()) {
                placePickerCallback.markAdNotFavouritePlace(mapPlace);
            } else {
                placePickerCallback.markAsFavouritePlace(mapPlace);
            }
        }
    }

    private boolean notNull() {
        return mapPlace != null && placePickerCallback != null;
    }
}
