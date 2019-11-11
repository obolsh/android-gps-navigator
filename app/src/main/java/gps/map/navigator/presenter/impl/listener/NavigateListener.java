package gps.map.navigator.presenter.impl.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IRouteListener;

public class NavigateListener implements IRouteListener {
    @Nullable
    private IRouteListener listener;

    public NavigateListener(@Nullable IRouteListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRouteStarted(@NonNull IRoute route) {
        if (listener != null) {
            listener.onRouteStarted(route);
        }
    }

    @Override
    public void onRouteStopped(@NonNull IRoute route) {
        if (listener != null) {
            listener.onRouteStopped(route);
        }
        invalidate();
    }

    @Override
    public void onRouteError(@NonNull IRoute route, @NonNull Exception reason) {
        if (listener != null) {
            listener.onRouteError(route, reason);
        }
        invalidate();
    }

    private void invalidate() {
        listener = null;
    }
}
