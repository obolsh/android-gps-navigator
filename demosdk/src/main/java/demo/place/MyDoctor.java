package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class MyDoctor extends MapPlace {

    public MyDoctor() {
        setId("my_doctor");
        setLastUsedTime(System.currentTimeMillis());
        setLongitude(400);
        setLatitude(400);
        setAddress("Kiev");
        setFavourite(true);
        setTitle("My Doctor");
        setLabel("Doctor");
    }
}
