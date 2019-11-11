package gps.map.navigator.presenter.impl.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class ShowRouteListener implements IRouteReadyListener {
    @Nullable
    private Presenter cache;
    @Nullable
    private IRouteReadyListener listener;

    public ShowRouteListener(@Nullable Presenter cache, @Nullable IRouteReadyListener listener) {
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
