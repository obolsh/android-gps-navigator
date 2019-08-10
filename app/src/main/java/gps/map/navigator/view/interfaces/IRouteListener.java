package gps.map.navigator.view.interfaces;

import gps.map.navigator.model.interfaces.IRoute;

public interface IRouteListener {

    void onRouteStarted(IRoute route);

    void onRouteStopped(IRoute route);

    void onRouteError(IRoute route, Exception reason);
}
