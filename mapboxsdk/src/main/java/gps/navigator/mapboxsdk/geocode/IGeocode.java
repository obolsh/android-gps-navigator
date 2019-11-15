package gps.navigator.mapboxsdk.geocode;

import androidx.annotation.Nullable;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;

public interface IGeocode {

    void searchForLocations(@Nullable String query, @Nullable IPlaceListener listener);

    void getPlaceByLocation(@Nullable IMapPlace place, @Nullable ReverseGeocodeListener listener);
}
