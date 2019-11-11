package gps.map.navigator.model.interfaces;

import androidx.annotation.NonNull;

public interface PlaceProxyListener {
    /**
     * place that was located.
     *
     * @param mapPlace - place.
     */
    void onPlaceLocated(@NonNull IMapPlace mapPlace);
}
