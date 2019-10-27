package demo.route;

import demo.place.MyLocation;
import demo.place.MyWork;
import gps.map.navigator.model.impl.data.Route;

public class RouteToWork extends Route {

    public RouteToWork() {
        setId("route_to_work");
        setTitle("Route to office");
        setOrigin(new MyLocation());
        setDestination(new MyWork());
        setLastUsedTime(System.currentTimeMillis());
    }
}
