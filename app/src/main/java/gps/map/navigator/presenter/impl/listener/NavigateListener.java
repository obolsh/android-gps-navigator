package gps.map.navigator.presenter.impl.listener;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IRouteListener;

public class NavigateListener implements IRouteListener {

    private IRouteListener routeListener;

    public NavigateListener(IRouteListener routeListener) {
        this.routeListener = routeListener;
    }

    @Override
    public void onRouteStarted(IRoute route) {
        if (routeListener != null) {
            routeListener.onRouteStarted(route);
        }
    }

    @Override
    public void onRouteStopped(IRoute route) {
        if (routeListener != null) {
            routeListener.onRouteStopped(route);
        }
        invalidate();
    }

    @Override
    public void onRouteError(IRoute route, Exception reason) {
        if (routeListener != null) {
            routeListener.onRouteError(route, reason);
        }
        invalidate();
    }

    private void invalidate() {
        routeListener = null;
    }
}
