package gps.map.navigator.presenter.impl;

import javax.inject.Inject;

import gps.map.navigator.model.MapType;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.impl.listener.FindAndShowListener;
import gps.map.navigator.presenter.impl.listener.FindPlaceListener;
import gps.map.navigator.presenter.impl.listener.NavigateListener;
import gps.map.navigator.presenter.impl.listener.ShowMeOnMapListener;
import gps.map.navigator.presenter.impl.listener.ShowRouteListener;
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
    MapSetting mapSetting;

    @Inject
    PresenterImpl() {
    }

    @Override
    public void showMeOnMap(IPlaceListener placeListener) {
        if (mapSdk != null) {
            mapSdk.showMeOnMap(new ShowMeOnMapListener(cache, placeListener));
        }
    }

    @Override
    public void enableTraffic(boolean enable) {
        MapSetting mapSetting = getMapSettings();
        boolean isDay = isDay(mapSetting);
        if (enable) {
            mapSetting.setMapType(isDay ? MapType.TRAFFIC_DAY : MapType.TRAFFIC_NIGHT);
        } else if (isDefaultMap(mapSetting)) {
            mapSetting.setMapType(isDay ? MapType.NORMAL_DAY : MapType.NORMAL_NIGHT);
        } else {
            mapSetting.setMapType(isDay ? MapType.SATELLITE_DAY : MapType.SATELLITE_NIGHT);
        }
        saveNewMapSettings(mapSetting);
    }

    private MapSetting getMapSettings() {
        if (hasCachedMapSettings()) {
            return cache.getMapSettings();
        } else {
            return mapSetting;
        }
    }

    private boolean hasCachedMapSettings() {
        return cache != null && cache.getMapSettings() != null;
    }

    private boolean isDay(MapSetting mapSetting) {
        int mapType = mapSetting.getMapType();
        return mapType == MapType.NORMAL_DAY
                || mapType == MapType.TRAFFIC_DAY
                || mapType == MapType.SATELLITE_DAY;
    }

    private void saveNewMapSettings(MapSetting mapSetting) {
        if (mapSdk != null && mapSetting != null) {
            mapSdk.setMapSettings(mapSetting);
        }

        if (cache != null && mapSetting != null) {
            cache.setMapSettings(mapSetting);
        }
    }

    @Override
    public void enableNightMode(boolean enable) {
        MapSetting mapSetting = getMapSettings();
        boolean isDefaultMap = isDefaultMap(mapSetting);
        boolean isTrafficMap = isTrafficMap(mapSetting);
        if (enable) {
            mapSetting.setMapType(
                    isDefaultMap ? MapType.TRAFFIC_NIGHT :
                            isTrafficMap ? MapType.TRAFFIC_NIGHT : MapType.SATELLITE_NIGHT);
        } else {
            mapSetting.setMapType(
                    isDefaultMap ? MapType.TRAFFIC_DAY :
                            isTrafficMap ? MapType.TRAFFIC_DAY : MapType.SATELLITE_DAY);
        }
        saveNewMapSettings(mapSetting);
    }

    private boolean isDefaultMap(MapSetting mapSetting) {
        int mapType = mapSetting.getMapType();
        return mapType == MapType.NORMAL_DAY
                || mapType == MapType.NORMAL_NIGHT;
    }

    private boolean isTrafficMap(MapSetting mapSetting) {
        int mapType = mapSetting.getMapType();
        return mapType == MapType.TRAFFIC_DAY
                || mapType == MapType.TRAFFIC_NIGHT;
    }

    @Override
    public void findAndShowPlace(IPlaceShowListener placeShowListener) {
        if (mapSdk != null) {
            mapSdk.findPlace(new FindAndShowListener(mapSdk, cache, placeShowListener));
        }
    }

    @Override
    public void showRoute(IRoute route, IRouteReadyListener routeReadyListener) {
        if (mapSdk != null) {
            mapSdk.showRoute(route, new ShowRouteListener(cache, routeReadyListener));
        }
    }

    @Override
    public void findPlace(IPlaceListener placeListener) {
        if (mapSdk != null) {
            mapSdk.findPlace(new FindPlaceListener(cache, placeListener));
        }
    }

    @Override
    public void navigate(IRoute route, IRouteListener routeListener) {
        if (mapSdk != null) {
            mapSdk.navigate(route, new NavigateListener(routeListener));
        }
    }

    @Override
    public void buildRoute(IPlaceHistoryListener placeHistoryListener) {
        if (placeHistoryListener != null) {
            if (cache != null) {
                placeHistoryListener.onHistoryPlacesFound(cache.getHistoryPlaces());
            } else {
                placeHistoryListener.onHistoryPlacesError(new Exception("Cache invalid"));
            }
        }
    }

}
