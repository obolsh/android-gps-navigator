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
        this.geocode = geocode;
        return this;
    }

    @Override
    public void searchForLocations(@Nullable String query, @Nullable IPlaceListener listener) {
        if (geocode != null) {
            geocode.searchForLocations(query, listener);
        }
    }

    @Nullable
    @Override
    public String getPlaceByLocation(@Nullable IMapPlace place) {
        return geocode != null && place != null ? geocode.getPlaceByLocation(place) : null;
    }
}
