package demo.route;

import demo.place.MyGym;
import demo.place.MyWork;
import gps.map.navigator.model.impl.data.Route;

public class RouteToGym extends Route {

    public RouteToGym() {
        setId("route_to_gym");
        setTitle("Route to doctor");
        setOrigin(new MyWork());
        setDestination(new MyGym());
        setLastUsedTime(System.currentTimeMillis());
    }
}
