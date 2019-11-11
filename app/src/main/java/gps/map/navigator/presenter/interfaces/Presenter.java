package gps.map.navigator.presenter.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public interface Presenter {
    /**
     * Show point on map with own location.
     *
     * @param placeListener - listener.
     */
    void showMeOnMap(@Nullable IPlaceListener placeListener);

    /**
     * Show/hide traffic on map.
     *
     * @param enable - true/false to show/hide.
     */
    void enableTraffic(boolean enable);

    /**
     * Show/hide night map.
     *
     * @param enable - true/false to show/hide.
     */
    void enableNightMode(boolean enable);

    /**
     * Show/hide satellite map.
     *
     * @param enable - true/false to show/hide.
     */
    void enableSatelliteMode(boolean enable);

    /**
     * Get traffic map state.
     *
     * @return true if enabled, false otherwise.
     */
    boolean hasTrafficMode();

    /**
     * Get night map state.
     *
     * @return true if enabled, false otherwise.
     */
    boolean hasNightMode();

    /**
     * Get satellite map state.
     *
     * @return true if enabled, false otherwise.
     */
    boolean hasSatelliteMode();

    /**
     * Open initial map view.
     */
    void showMap();

    /**
     * Show place on map.
     *
     * @param place             - place to be shown.
     * @param placeShowListener - listener to get callback.
     */
    void showPlace(@NonNull IMapPlace place, @Nullable IPlaceShowListener placeShowListener);

    /**
     * Show route on map.
     *
     * @param route              - place to be shown.
     * @param routeReadyListener - listener to get callback.
     */
    void showRoute(@NonNull IRoute route, @Nullable IRouteReadyListener routeReadyListener);

    /**
     * Find place on map.
     *
     * @param query         - query to search.
     * @param placeListener - listener to get callback.
     */
    void findPlace(@NonNull String query, @Nullable IPlaceListener placeListener);

    /**
     * Navigate route.
     *
     * @param route         - route to navigate.
     * @param routeListener - listener to get callback.
     */
    void navigate(@NonNull IRoute route, @Nullable IRouteListener routeListener);

    /**
     * Build route.
     *
     * @param placeHistoryListener - listener to get callback about history places.
     */
    void buildRoute(@Nullable IPlaceHistoryListener placeHistoryListener);
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
