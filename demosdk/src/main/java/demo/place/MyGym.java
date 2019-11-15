package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class MyGym extends MapPlace {

    public MyGym() {
        setId("my_gym");
        setLastUsedTime(System.currentTimeMillis());
        setLongitude(200);
        setLatitude(200);
        setAddress("Kiev");
        setFavourite(true);
        setTitle("My Gym");
        setLabel("Gym");
    }
}
