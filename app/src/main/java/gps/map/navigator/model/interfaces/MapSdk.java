package gps.map.navigator.model.interfaces;

import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public interface MapSdk {

    void showMeOnMap(IPlaceListener placeListener);

    void setMapSettings(MapSetting mapSettings);

    void showPlace(IMapPlace place, IPlaceShowListener placeShowListener);

    void showRoute(IRoute route, IRouteReadyListener routeListener);

    void findPlace(IPlaceListener placeListener);

    IMapPlace getMyLocation();

    IMapPlace getLastPickedPlace();

    void navigate(IRoute route, IRouteListener routeListener);

}
