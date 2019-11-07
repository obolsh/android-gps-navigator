package gps.map.navigator.presenter.impl.listener;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IRouteListener;

public class NavigateListener implements IRouteListener {

    private IRouteListener listener;

    public NavigateListener(IRouteListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRouteStarted(IRoute route) {
        if (listener != null) {
            listener.onRouteStarted(route);
        }
    }

    @Override
    public void onRouteStopped(IRoute route) {
        if (listener != null) {
            listener.onRouteStopped(route);
        }
        invalidate();
    }

    @Override
    public void onRouteError(IRoute route, Exception reason) {
        if (listener != null) {
            listener.onRouteError(route, reason);
        }
        invalidate();
    }

    private void invalidate() {
        listener = null;
    }
}
