package gps.map.navigator.presenter.impl.listener;

import androidx.annotation.NonNull;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class ShowRouteListener implements IRouteReadyListener {

    private Cache cache;
    private IRouteReadyListener listener;

    public ShowRouteListener(Cache cache, IRouteReadyListener listener) {
        this.cache = cache;
        this.listener = listener;
    }

    @Override
    public void onRouteReady(@NonNull IRoute route) {
        if (cache != null) {
            cache.setLastRoute(route);
        }
        if (listener != null) {
            listener.onRouteReady(route);
        }
        invalidate();
    }

    @Override
    public void onRouteFailed(@NonNull Exception reason) {
        if (listener != null) {
            listener.onRouteFailed(reason);
        }
        invalidate();
    }

    private void invalidate() {
        cache = null;
        listener = null;
    }
}
