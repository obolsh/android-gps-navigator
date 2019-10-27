package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class MyLocation extends MapPlace {

    public MyLocation() {
        setId("my_place");
        setLastUsedTime(System.currentTimeMillis());
        setX(100);
        setY(100);
        setAddress("Kiev");
        setFavourite(true);
        setTitle("My Location");
        setLabel("My Label");
    }
}
