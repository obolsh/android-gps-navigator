package gps.map.navigator.model.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

public interface PlaceProxyListener {
    /**
     * place that was located.
     *
     * @param mapPlace - place.
     */
    void onPlaceLocated(@NonNull IMapPlace mapPlace);

    /**
     * places that was located.
     *
     * @param mapPlaces - place.
     */
    void onPlacesLocated(@NonNull List<IMapPlace> mapPlaces);
}
