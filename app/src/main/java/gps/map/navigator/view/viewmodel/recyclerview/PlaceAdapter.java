package gps.map.navigator.view.viewmodel.recyclerview;

import android.widget.Filterable;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface PlaceAdapter extends Filterable {
    /**
     * Get list of places that was initially added.
     *
     * @return - list of places or null.
     */
    List<IMapPlace> getOriginalPlacesList();

    /**
     * Change list of visible placees.
     *
     * @param placeList - list of places.
     */
    void changePlacesList(List<IMapPlace> placeList);

    /**
     * Show single place in list.
     *
     * @param mapPlace - place to be shown.
     */
    void showSinglePlace(IMapPlace mapPlace);

    /**
     * remove place from position.
     *
     * @param position - place position.
     * @param place    - place to be removed.
     */
    void removePlace(int position, IMapPlace place);

    /**
     * update settings for place.
     *
     * @param update - place to be updated.
     */
    void updatePlace(IMapPlace update);

    /**
     * Save list of initial placees.
     *
     * @param places - list of places.
     */
    void setInitialPlacesList(List<IMapPlace> places);
}
