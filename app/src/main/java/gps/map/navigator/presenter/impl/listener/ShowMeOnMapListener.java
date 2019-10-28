package gps.map.navigator.presenter.impl.listener;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;

public class ShowMeOnMapListener implements IPlaceListener {
    private Cache cache;
    private IPlaceListener placeListener;

    public ShowMeOnMapListener(Cache cache, IPlaceListener placeListener) {
        this.cache = cache;
        this.placeListener = placeListener;
    }

    @Override
    public void onPlaceLocated(IMapPlace place) {
        if (cache != null) {
            cache.setMyLocation(place);
            cache.setLastOrigin(place);
        }
        if (placeListener != null) {
            placeListener.onPlaceLocated(place);
        }
        invalidate();
    }

    @Override
    public void onPlaceLocationFailed(Exception reason) {
        if (placeListener != null) {
            placeListener.onPlaceLocationFailed(reason);
        }
        invalidate();
    }

    private void invalidate() {
        cache = null;
        placeListener = null;
    }
}
