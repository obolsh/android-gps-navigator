package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class MyBar extends MapPlace {

    public MyBar() {
        setId("my_bar");
        setLastUsedTime(System.currentTimeMillis());
        setLongitude(200);
        setLatitude(200);
        setAddress("Kiev");
        setFavourite(true);
        setTitle("My Bar");
        setLabel("Bar");
    }
}
