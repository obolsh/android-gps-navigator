package demo.route;

import demo.place.MyBar;
import demo.place.MyLocation;
import gps.map.navigator.model.impl.data.Route;

public class RouteToBar extends Route {

    public RouteToBar() {
        setId("route_to_bar");
        setTitle("Route to bar");
        setOrigin(new MyLocation());
        setDestination(new MyBar());
        setLastUsedTime(System.currentTimeMillis());
    }
}
