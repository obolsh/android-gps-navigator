package gps.map.navigator.view.ui.fragment.listener;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;

public class OriginChangeListener implements PlaceProxyListener {
    @Inject
    ISwipeRoute swipeRoute;
    @Inject
    Cache cache;

    @Inject
    OriginChangeListener() {
    }

    @Override
    public void onPlaceLocated(@NonNull IMapPlace mapPlace) {
        if (!placesAreTheSame(mapPlace, cache.getLastDestination())) {
            swipeRoute.setOnlyOrigin(mapPlace);
        }
    }

    @Override
    public void onPlacesLocated(@NonNull List<IMapPlace> mapPlaces) {

    }

    private boolean placesAreTheSame(IMapPlace place, IMapPlace comparing) {
        return place != null && comparing != null
                && place.getLongitude() == comparing.getLongitude()
                && place.getLatitude() == comparing.getLatitude();
    }
}
