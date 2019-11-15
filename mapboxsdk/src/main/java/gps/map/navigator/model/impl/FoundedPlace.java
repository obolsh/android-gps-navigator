package gps.map.navigator.model.impl;

import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

import gps.map.navigator.model.impl.data.MapPlace;

public class FoundedPlace extends MapPlace {

    public FoundedPlace(CarmenFeature item) {
        setId(item.id());
        setLastUsedTime(System.currentTimeMillis());

        setAddress(item.placeName());
        setTitle(item.text());


        Point point = item.center();
        setLatitude(point.latitude());
        setLongitude(point.longitude());
    }
}
