package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class FoundedRoute_OriginPlace extends MapPlace {

    public FoundedRoute_OriginPlace() {
        setId("founded_place_origin");
        setLastUsedTime(System.currentTimeMillis());
        setLongitude(100);
        setLatitude(100);
        setAddress("Kiev");
        setFavourite(false);
        setTitle("founded_place_origin_title");
        setLabel("founded_place_origin_label");
    }
}
