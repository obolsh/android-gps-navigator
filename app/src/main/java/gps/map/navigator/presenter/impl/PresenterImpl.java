package gps.map.navigator.presenter.impl;

import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.IRoute;
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
    public void showMeOnMap(IPlaceListener placeListener) {
        if (mapSdk != null && placeListener != null) {
            mapSdk.showMeOnMap(new ShowMeOnMapListener(cache, placeListener));
        }
    }

    @Override
    public void showMap() {
        if (mapSdk != null) {
            mapSdk.showMap();
        }
    }

    @Override
    public void showPlace(IMapPlace place, IPlaceShowListener placeShowListener) {
        if (mapSdk != null && placeShowListener != null) {
            mapSdk.showPlace(place, placeShowListener);
        }
    }

    @Override
    public void showRoute(IRoute route, IRouteReadyListener routeReadyListener) {
        if (mapSdk != null && routeReadyListener != null) {
            mapSdk.showRoute(route, new ShowRouteListener(cache, routeReadyListener));
        }
    }

    @Override
    public void findPlace(IPlaceListener placeListener) {
        if (mapSdk != null && placeListener != null) {
            mapSdk.findPlace(new FindPlaceListener(cache, placeListener));
        }
    }

    @Override
    public void navigate(IRoute route, IRouteListener routeListener) {
        if (mapSdk != null && routeListener != null) {
            mapSdk.navigate(route, new NavigateListener(routeListener));
        }
    }

    @Override
    public void buildRoute(IPlaceHistoryListener placeHistoryListener) {
        if (placeHistoryListener != null) {
            List<IMapPlace> places = cache.getHistoryPlaces();
            if (places != null && !places.isEmpty()) {
                placeHistoryListener.onHistoryPlacesFound(places);
            } else {
                placeHistoryListener.onHistoryPlacesError(new Exception("Cache invalid"));
            }
        }
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
