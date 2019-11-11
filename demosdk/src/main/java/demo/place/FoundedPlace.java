package demo.place;

import gps.map.navigator.model.impl.data.MapPlace;

public class FoundedPlace extends MapPlace {

    public FoundedPlace() {
        setId("founded_place_" + hashCode());
        setLastUsedTime(System.currentTimeMillis());
        setX(100);
        setY(100);
        setAddress("Kiev");
        setFavourite(false);
        setTitle("Location_" + hashCode());
        setLabel("Label_" + hashCode());
    }
}
