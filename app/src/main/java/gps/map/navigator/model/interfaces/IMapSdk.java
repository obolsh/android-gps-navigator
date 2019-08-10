package gps.map.navigator.model.interfaces;

import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;

public interface IMapSdk {

    void showMeOnMap(IPlaceListener placeListener);

    void setMapSettings(IMapSettings mapSettings);

    void showPlace(IMapPlace place, IPlaceShowListener placeShowListener);

    void showRoute(IRoute route, IRouteListener routeListener);

    void findPlace(IPlaceListener placeListener);

    IMapPlace getMyLocation();

    IMapPlace getLastPickedPlace();

    void navigate(IRoute route, IRouteListener routeListener);

}
