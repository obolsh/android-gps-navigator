package gps.map.navigator.model.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface Cache {
    /**
     * Get list of history places.
     *
     * @return - array of history places, or null.
     */
    @Nullable
    List<IMapPlace> getHistoryPlaces();

    /**
     * Set list of history places.
     *
     * @param historyPlaces - list to be saved.
     */
    void setHistoryPlaces(@Nullable List<IMapPlace> historyPlaces);

    /**
     * Get own location.
     *
     * @return - own location or null.
     */
    @Nullable
    IMapPlace getMyLocation();

    /**
     * Save own location.
     *
     * @param myLocation - own location.
     */
    void setMyLocation(@Nullable IMapPlace myLocation);

    /**
     * Get last origin place.
     *
     * @return - last origin or null.
     */
    @Nullable
    IMapPlace getLastOrigin();

    /**
     * Save last origin place.
     *
     * @param lastOrigin - last origin.
     */
    void setLastOrigin(@Nullable IMapPlace lastOrigin);

    /**
     * Get last destination place.
     *
     * @return - last destination or null.
     */
    @Nullable
    IMapPlace getLastDestination();

    /**
     * Save last destination place.
     *
     * @param lastDestination - last destination.
     */
    void setLastDestination(@Nullable IMapPlace lastDestination);

    /**
     * Get last route.
     *
     * @return - last route or null.
     */
    @Nullable
    IRoute getLastRoute();

    /**
     * Save last route.
     *
     * @param lastRoute - last route.
     */
    void setLastRoute(@Nullable IRoute lastRoute);

    /**
     * Get last place.
     *
     * @return - last place or null.
     */
    @Nullable
    IMapPlace getLastPlace();

    /**
     * Save last place.
     *
     * @param lastPlace - last place.
     */
    void setLastPlace(@Nullable IMapPlace lastPlace);

    /**
     * Get map settings.
     *
     * @return - map settings or null.
     */
    @Nullable
    MapSetting getMapSettings();

    /**
     * save map settings.
     *
     * @param mapSettings - map settings.
     */
    void setMapSettings(@Nullable MapSetting mapSettings);

    /**
     * Get byte array from cache by key.
     *
     * @param key - key
     * @return - byte array or null.
     */
    @Nullable
    byte[] getRawData(@NonNull String key);

    /**
     * Save byte array to cache.
     *
     * @param key     - key.
     * @param rawData - data to be saved.
     */
    void setRawData(@Nullable String key, @Nullable byte[] rawData);

    /**
     * Remove history place from cache.
     *
     * @param placeToRemove - place to be removed.
     */
    void removeHistoryPlace(@Nullable IMapPlace placeToRemove);

    /**
     * add new place to history.
     *
     * @param newPlace - place to add.
     */
    void addNewHistoryPlace(@Nullable IMapPlace newPlace);
}
