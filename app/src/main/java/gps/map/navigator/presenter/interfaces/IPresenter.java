package gps.map.navigator.presenter.interfaces;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public interface IPresenter {

    void showMeOnMap(IPlaceListener placeListener);

    void enableTraffic(boolean enable);

    void enableNightMode(boolean enable);

    void findAndShowPlace(IPlaceShowListener placeShowListener);

    void showRoute(IRoute route, IRouteReadyListener routeReadyListener);

    void findPlace(IPlaceListener placeListener);

    void navifate(IRoute route, IRouteListener routeListener);

    void buildRoute(IPlaceHistoryListener placeHistoryListener);
}
