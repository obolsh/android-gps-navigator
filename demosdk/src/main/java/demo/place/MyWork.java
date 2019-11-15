package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class MyWork extends MapPlace {

    public MyWork() {
        setId("my_work");
        setLastUsedTime(System.currentTimeMillis());
        setLongitude(200);
        setLatitude(200);
        setAddress("Kiev");
        setFavourite(true);
        setTitle("My Work");
        setLabel("Office");
    }
}
