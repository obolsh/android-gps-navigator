package gps.map.navigator.view.ui.fragment.listener;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface ICachedPlaceCallback {

    void setHistoryPlaces(List<IMapPlace> placeList);
}
