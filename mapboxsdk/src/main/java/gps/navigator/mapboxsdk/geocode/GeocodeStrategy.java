package gps.navigator.mapboxsdk.geocode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;

public class GeocodeStrategy implements IGeocode {

    private static final GeocodeStrategy instance = new GeocodeStrategy();

    private GeocodeStrategy() {
    }

    public static GeocodeStrategy getInstance() {
        return instance;
    }

    @Nullable
    private IGeocode geocode;

    public GeocodeStrategy setStategy(@NonNull IGeocode geocode) {
        if (this.geocode == null) {
            this.geocode = geocode;
        }
        return this;
    }

    @Override
    public void searchForLocations(@Nullable String query, @Nullable IPlaceListener listener) {
        if (geocode != null) {
            geocode.searchForLocations(query, listener);
        }
    }

    @Override
    public void getPlaceByLocation(@Nullable IMapPlace place, @Nullable ReverseGeocodeListener listener) {
        if (geocode != null && place != null && listener != null) {
            geocode.getPlaceByLocation(place, listener);
        }
    }
}
