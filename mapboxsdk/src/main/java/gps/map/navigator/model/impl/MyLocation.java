package gps.map.navigator.model.impl;

import gps.map.navigator.model.impl.data.MapPlace;

public class MyLocation extends MapPlace {

    public MyLocation() {
        setId("my_location");
        setLastUsedTime(System.currentTimeMillis());
        setTitle("My location");
        setFavourite(true);
        setLabel("My place");
        setAddress("Last location");
    }
}
