package gps.map.navigator.view.ui.fragment.listener;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface ISwipeRoute {

    void swipeOriginAndDestination();

    void setOnlyOrigin(IMapPlace origin);

    void setOnlyDestination(IMapPlace destination);
}
