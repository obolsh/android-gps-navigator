package gps.map.navigator.view.ui.fragment.listener;

import androidx.annotation.NonNull;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface IPlacePickerCallback {
    /**
     * Set last picked by user place.
     *
     * @param mapPlace - place.
     */
    void setNewPickedPlace(@NonNull IMapPlace mapPlace);

    /**
     * delete place from history.
     *
     * @param position - position in list.
     * @param mapPlace - place to be deleted.
     */
    void deleteHistoryPlace(int position, @NonNull IMapPlace mapPlace);

    /**
     * Mark place as favourite.
     *
     * @param mapPlace - place to be marked.
     */
    void markAsFavouritePlace(@NonNull IMapPlace mapPlace);

    /**
     * Mark place as not favourite.
     *
     * @param mapPlace - place to be marked.
     */
    void markAdNotFavouritePlace(@NonNull IMapPlace mapPlace);

    /**
     * Set last place found in external source but not cache.
     *
     * @param mapPlace - place.
     */
    void setNewFoundPlace(@NonNull IMapPlace mapPlace);
}
