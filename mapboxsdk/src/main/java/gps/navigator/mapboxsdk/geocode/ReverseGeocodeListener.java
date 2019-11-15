package gps.navigator.mapboxsdk.geocode;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface ReverseGeocodeListener {

    void onPlaceDetected(IMapPlace mapPlace);

    void onPlaceDetectFailed(Exception reason);
}
