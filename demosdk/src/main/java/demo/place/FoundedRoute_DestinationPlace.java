package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class FoundedRoute_DestinationPlace extends MapPlace {

    public FoundedRoute_DestinationPlace() {
        setId("founded_place_destination");
        setLastUsedTime(System.currentTimeMillis());
        setLongitude(200);
        setLatitude(200);
        setAddress("Kiev");
        setFavourite(false);
        setTitle("founded_place_destination_title");
        setLabel("founded_place_destination_label");
    }
}
