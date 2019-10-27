package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class MyWork extends MapPlace {

    public MyWork() {
        setId("my_work");
        setLastUsedTime(System.currentTimeMillis());
        setX(200);
        setY(200);
        setAddress("Kiev");
        setFavourite(true);
        setTitle("My Work");
        setLabel("Office");
    }
}
