package gps.map.navigator.presenter.interfaces;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public interface Presenter {

    void showMeOnMap(IPlaceListener placeListener);

    void enableTraffic(boolean enable);

    void enableNightMode(boolean enable);

    void enableSatelliteMode(boolean enable);

    boolean hasTrafficMode();

    boolean hasNightMode();

    boolean hasSatelliteMode();

    void showMap();

    void findAndShowPlace(IPlaceShowListener placeShowListener);

    void showRoute(IRoute route, IRouteReadyListener routeReadyListener);

    void findPlace(IPlaceListener placeListener);

    void navigate(IRoute route, IRouteListener routeListener);

    void buildRoute(IPlaceHistoryListener placeHistoryListener);
}
