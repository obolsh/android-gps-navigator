package gps.map.navigator.presenter.impl.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;

public class FindPlaceListener implements IPlaceListener {
    @Nullable
    private Cache cache;
    @Nullable
    private IPlaceListener listener;

    public FindPlaceListener(@Nullable Cache cache, @Nullable IPlaceListener listener) {
        this.cache = cache;
        this.listener = listener;
    }

    @Override
    public void onPlaceLocated(@NonNull IMapPlace place) {
        if (cache != null) {
            cache.setLastPlace(place);
        }
        if (listener != null) {
            listener.onPlaceLocated(place);
        }
        invalidate();
    }

    @Override
    public void onPlaceLocationFailed(@NonNull Exception reason) {
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
