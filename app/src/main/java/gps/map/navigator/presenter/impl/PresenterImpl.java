package gps.map.navigator.presenter.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.presenter.impl.listener.FindPlaceListener;
import gps.map.navigator.presenter.impl.listener.NavigateListener;
import gps.map.navigator.presenter.impl.listener.ShowMeOnMapListener;
import gps.map.navigator.presenter.impl.listener.ShowRouteListener;
import gps.map.navigator.presenter.interfaces.IMapTypeController;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class PresenterImpl implements Presenter {
    @Inject
    MapSdk mapSdk;
    @Inject
    Cache cache;
    @Inject
    IMapTypeController mapTypeController;

    @Inject
    PresenterImpl() {
    }

    @Override
    public void showMeOnMap(@Nullable IPlaceListener placeListener) {
        if (mapSdk != null && placeListener != null) {
            mapSdk.showMeOnMap(new ShowMeOnMapListener(this, placeListener));
        }
    }

    @Override
    public void showMap() {
        if (mapSdk != null) {
            mapSdk.showMap();
        }
    }

    @Override
    public void showPlace(@NonNull IMapPlace place, @Nullable IPlaceShowListener placeShowListener) {
        if (mapSdk != null && placeShowListener != null) {
            mapSdk.showPlace(place, placeShowListener);
        }
    }

    @Override
    public void showRoute(@NonNull IRoute route, @Nullable IRouteReadyListener routeReadyListener) {
        if (mapSdk != null && routeReadyListener != null) {
            mapSdk.showRoute(route, new ShowRouteListener(this, routeReadyListener));
        }
    }

    @Override
    public void findPlace(@NonNull String query, @Nullable IPlaceListener placeListener) {
        if (mapSdk != null && placeListener != null) {
            mapSdk.findPlace(query, new FindPlaceListener(this, placeListener));
        }
    }

    @Override
    public void navigate(@NonNull IRoute route, @Nullable IRouteListener routeListener) {
        if (mapSdk != null && routeListener != null) {
            mapSdk.navigate(route, new NavigateListener(routeListener));
        }
    }

    @Override
    public void buildRoute(@Nullable IPlaceHistoryListener placeHistoryListener) {
        if (placeHistoryListener != null) {
            List<IMapPlace> places = cache.getHistoryPlaces();
            if (places != null && !places.isEmpty()) {
                placeHistoryListener.onHistoryPlacesFound(places);
            } else {
                placeHistoryListener.onHistoryPlacesError(new Exception("Cache invalid"));
            }
        }
    }

    @Nullable
    @Override
    public List<IMapPlace> getHistoryPlaces() {
        return cache.getHistoryPlaces();
    }

    @Override
    public void setHistoryPlaces(@Nullable List<IMapPlace> historyPlaces) {
        cache.setHistoryPlaces(historyPlaces);
    }

    @Nullable
    @Override
    public IMapPlace getMyLocation() {
        return cache.getMyLocation();
    }

    @Override
    public void setMyLocation(@Nullable IMapPlace myLocation) {
        cache.setMyLocation(myLocation);
    }

    @Nullable
    @Override
    public IMapPlace getLastOrigin() {
        return cache.getLastOrigin();
    }

    @Override
    public void setLastOrigin(@Nullable IMapPlace lastOrigin) {
        cache.setLastOrigin(lastOrigin);
    }

    @Nullable
    @Override
    public IMapPlace getLastDestination() {
        return cache.getLastDestination();
    }

    @Override
    public void setLastDestination(@Nullable IMapPlace lastDestination) {
        cache.setLastDestination(lastDestination);
    }

    @Nullable
    @Override
    public IRoute getLastRoute() {
        return cache.getLastRoute();
    }

    @Override
    public void setLastRoute(@Nullable IRoute lastRoute) {
        cache.setLastRoute(lastRoute);
    }

    @Nullable
    @Override
    public IMapPlace getLastPlace() {
        return cache.getLastPlace();
    }

    @Override
    public void setLastPlace(@Nullable IMapPlace lastPlace) {
        cache.setLastPlace(lastPlace);
    }

    @Nullable
    @Override
    public byte[] getRawData(@NonNull String key) {
        return cache.getRawData(key);
    }

    @Override
    public void setRawData(@Nullable String key, @Nullable byte[] rawData) {
        cache.setRawData(key, rawData);
    }

    @Override
    public void removeHistoryPlace(@Nullable IMapPlace placeToRemove) {
        cache.removeHistoryPlace(placeToRemove);
    }

    @Override
    public void addNewHistoryPlace(@Nullable IMapPlace newPlace) {
        cache.addNewHistoryPlace(newPlace);
    }

    @Override
    public void enableTraffic(boolean enable) {
        mapTypeController.enableTraffic(enable);
    }

    @Override
    public void enableNightMode(boolean enable) {
        mapTypeController.enableNightMode(enable);
    }

    @Override
    public void enableSatelliteMode(boolean enable) {
        mapTypeController.enableSatellite(enable);
    }

    @Override
    public boolean hasTrafficMode() {
        return mapTypeController.hasTrafficMode();
    }

    @Override
    public boolean hasNightMode() {
        return mapTypeController.hasNightMode();
    }

    @Override
    public boolean hasSatelliteMode() {
        return mapTypeController.hasSatelliteMode();
    }

}
