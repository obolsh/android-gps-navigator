package gps.map.navigator.presenter.impl.listener;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;

public class ShowMeOnMapListener implements IPlaceListener {
    private Cache cache;
    private IPlaceListener listener;

    public ShowMeOnMapListener(Cache cache, IPlaceListener listener) {
        this.cache = cache;
        this.listener = listener;
    }

    @Override
    public void onPlaceLocated(IMapPlace place) {
        if (cache != null) {
            cache.setMyLocation(place);
        }
        if (listener != null) {
            listener.onPlaceLocated(place);
        }
        invalidate();
    }

    @Override
    public void onPlaceLocationFailed(Exception reason) {
        if (listener != null) {
            listener.onPlaceLocationFailed(reason);
        }
        invalidate();
    }

    private void invalidate() {
        cache = null;
        listener = null;
    }
}
