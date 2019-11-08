package gps.map.navigator.view.ui.fragment.listener;

import androidx.annotation.NonNull;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface ISwipeRoute {
    /**
     * Make swipe for origin & destination in last route.
     */
    void swipeOriginAndDestination();

    /**
     * Clean destination & set new place as origin.
     *
     * @param origin - place to be set as origin.
     */
    void setOnlyOrigin(@NonNull IMapPlace origin);

    /**
     * Clean origin & set new place as destination.
     *
     * @param destination - place to be set as destination.
     */
    void setOnlyDestination(@NonNull IMapPlace destination);
}
