package gps.map.navigator.model.interfaces;

import java.util.List;

public interface Cache {
    /**
     * Get list of history places.
     *
     * @return - array of history places, or null.
     */
    List<IMapPlace> getHistoryPlaces();

    /**
     * Set list of history places.
     *
     * @param historyPlaces - list to be saved.
     */
    void setHistoryPlaces(List<IMapPlace> historyPlaces);

    /**
     * Get own location.
     *
     * @return - own location or null.
     */
    IMapPlace getMyLocation();

    /**
     * Save own location.
     *
     * @param myLocation - own location.
     */
    void setMyLocation(IMapPlace myLocation);

    /**
     * Get last origin place.
     *
     * @return - last origin or null.
     */
    IMapPlace getLastOrigin();

    /**
     * Save last origin place.
     *
     * @param lastOrigin - last origin.
     */
    void setLastOrigin(IMapPlace lastOrigin);

    /**
     * Get last destination place.
     *
     * @return - last destination or null.
     */
    IMapPlace getLastDestination();

    /**
     * Save last destination place.
     *
     * @param lastDestination - last destination.
     */
    void setLastDestination(IMapPlace lastDestination);

    /**
     * Get last route.
     *
     * @return - last route or null.
     */
    IRoute getLastRoute();

    /**
     * Save last route.
     *
     * @param lastRoute - last route.
     */
    void setLastRoute(IRoute lastRoute);

    /**
     * Get last place.
     *
     * @return - last place or null.
     */
    IMapPlace getLastPlace();

    /**
     * Save last place.
     *
     * @param lastPlace - last place.
     */
    void setLastPlace(IMapPlace lastPlace);

    /**
     * Get map settings.
     *
     * @return - map settings or null.
     */
    MapSetting getMapSettings();

    /**
     * save map settings.
     *
     * @param mapSettings - map settings.
     */
    void setMapSettings(MapSetting mapSettings);

    /**
     * Get byte array from cache by key.
     *
     * @param key - key
     * @return - byte array or null.
     */
    byte[] getRawData(String key);

    /**
     * Save byte array to cache.
     *
     * @param key     - key.
     * @param rawData - data to be saved.
     */
    void setRawData(String key, byte[] rawData);

    /**
     * Remove history place from cache.
     *
     * @param placeToRemove - place to be removed.
     */
    void removeHistoryPlace(IMapPlace placeToRemove);

    /**
     * add new place to history.
     *
     * @param newPlace - place to add.
     */
    void addNewHistoryPlace(IMapPlace newPlace);
}
