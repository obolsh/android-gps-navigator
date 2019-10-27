package gps.map.navigator.model.impl.sdk;


import javax.inject.Inject;

import demo.place.FoundedPlace;
import demo.place.MyLocation;
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
        placeListener.onPlaceLocated(new MyLocation());
    }

    @Override
    public void setMapSettings(MapSetting mapSettings) {

    }

    @Override
    public void showPlace(IMapPlace place, IPlaceShowListener placeShowListener) {
        placeShowListener.onPlaceShown(place);
    }

    @Override
    public void showRoute(IRoute route, IRouteReadyListener routeListener) {
        routeListener.onRouteReady(route);
    }

    @Override
    public void findPlace(IPlaceListener placeListener) {
        placeListener.onPlaceLocated(new FoundedPlace());
    }

    @Override
    public IMapPlace getMyLocation() {
        return new MyLocation();
    }

    @Override
    public IMapPlace getLastPickedPlace() {
        return new FoundedPlace();
    }

    @Override
    public void navigate(IRoute route, IRouteListener routeListener) {
        routeListener.onRouteStarted(route);
    }

    @Override
    public void showMap() {

    }
}
