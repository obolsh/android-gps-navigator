package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class MyLocation extends MapPlace {

    public MyLocation() {
        setId("my_place");
        setLastUsedTime(System.currentTimeMillis());
        setLongitude(100);
        setLatitude(100);
        setAddress("Kiev");
        setFavourite(true);
        setTitle("My Location");
        setLabel("My Label");
    }
}
