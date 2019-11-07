package gps.map.navigator.view.ui.fragment.listener;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface ICachedPlaceCallback {
    /**
     * Set history places.
     *
     * @param placeList - list of places.
     */
    void setHistoryPlaces(List<IMapPlace> placeList);
}
