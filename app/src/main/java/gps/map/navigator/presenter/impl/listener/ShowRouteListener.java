package gps.map.navigator.presenter.impl.listener;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class ShowRouteListener implements IRouteReadyListener {

    private Cache cache;
    private IRouteReadyListener routeReadyListener;

    public ShowRouteListener(Cache cache, IRouteReadyListener routeReadyListener) {
        this.cache = cache;
        this.routeReadyListener = routeReadyListener;
    }

    @Override
    public void onRouteReady(IRoute route) {
        if (cache != null) {
            cache.setLastRoute(route);
        }
        if (routeReadyListener != null) {
            routeReadyListener.onRouteReady(route);
        }
        invalidate();
    }

    @Override
    public void onRouteFailed(Exception reason) {
        if (routeReadyListener != null) {
            routeReadyListener.onRouteFailed(reason);
        }
        invalidate();
    }

    private void invalidate() {
        cache = null;
        routeReadyListener = null;
    }
}
