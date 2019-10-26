package gps.map.navigator.model.impl.sdk;


import javax.inject.Inject;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class MapBoxSdkImpl implements MapSdk {

    @Inject
    public MapBoxSdkImpl() {
    }

    @Override
    public void showMeOnMap(IPlaceListener placeListener) {

    }

    @Override
    public void setMapSettings(MapSetting mapSettings) {

    }

    @Override
    public void showPlace(IMapPlace place, IPlaceShowListener placeShowListener) {

    }

    @Override
    public void showRoute(IRoute route, IRouteReadyListener routeListener) {

    }

    @Override
    public void findPlace(IPlaceListener placeListener) {

    }

    @Override
    public IMapPlace getMyLocation() {
        return null;
    }

    @Override
    public IMapPlace getLastPickedPlace() {
        return null;
    }

    @Override
    public void navigate(IRoute route, IRouteListener routeListener) {

    }

    @Override
    public void showMap() {

    }
}
