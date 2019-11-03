package gps.map.navigator.view.ui.fragment.listener;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface IPlacePickerCallback {

    void setNewPickedPlace(IMapPlace mapPlace);

    void deleteHistoryPlace(int position, IMapPlace mapPlace);

    void markAdFavouritePlace(IMapPlace mapPlace);

    void markAdNotFavouritePlace(IMapPlace mapPlace);
}
