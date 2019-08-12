package gps.map.navigator.model.impl.common;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IMapSdk;
import gps.map.navigator.model.interfaces.IMapSetting;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class EmptyMapSdk implements IMapSdk {

    @Override
    public void showMeOnMap(IPlaceListener placeListener) {

    }

    @Override
    public void setMapSettings(IMapSetting mapSettings) {

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
}
