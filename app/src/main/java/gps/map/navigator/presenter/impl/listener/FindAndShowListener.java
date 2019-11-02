package gps.map.navigator.presenter.impl.listener;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;

@Deprecated
public class FindAndShowListener implements IPlaceListener {

    private MapSdk mapSdk;
    private Cache cache;
    private IPlaceShowListener placeShowListener;

    public FindAndShowListener(MapSdk mapSdk, Cache cache, IPlaceShowListener placeShowListener) {
        this.mapSdk = mapSdk;
        this.cache = cache;
        this.placeShowListener = placeShowListener;
    }

    @Override
    public void onPlaceLocated(IMapPlace place) {
        if (cache != null) {
            cache.setLastPlace(place);
        }
        if (mapSdk != null) {
            mapSdk.showPlace(place, placeShowListener);
        }
        invalidate();
    }

    @Override
    public void onPlaceLocationFailed(Exception reason) {
        if (placeShowListener != null) {
            placeShowListener.onPlaceShowFailed(reason);
        }
        invalidate();
    }

    private void invalidate() {
        cache = null;
        mapSdk = null;
        placeShowListener = null;
    }
}
