package gps.map.navigator.model.interfaces;

public interface PlaceProxyListener {
    /**
     * place that was located.
     *
     * @param mapPlace - place.
     */
    void onPlaceLocated(IMapPlace mapPlace);
}
