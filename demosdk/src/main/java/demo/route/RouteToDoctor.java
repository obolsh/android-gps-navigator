package demo.route;

import demo.place.MyDoctor;
import demo.place.MyLocation;
import gps.map.navigator.model.impl.data.Route;

public class RouteToDoctor extends Route {

    public RouteToDoctor() {
        setId("route_to_doctor");
        setTitle("Route to doctor");
        setOrigin(new MyLocation());
        setDestination(new MyDoctor());
        setLastUsedTime(System.currentTimeMillis());
    }
}
