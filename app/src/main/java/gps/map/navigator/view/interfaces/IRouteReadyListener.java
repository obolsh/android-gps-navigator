package gps.map.navigator.view.interfaces;

import gps.map.navigator.model.interfaces.IRoute;

public interface IRouteReadyListener {

    void onRouteReady(IRoute route);

    void onRouteFailed(Exception reason);
}
