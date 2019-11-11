package demo.route;

import demo.place.FoundedRoute_DestinationPlace;
import demo.place.FoundedRoute_OriginPlace;
import gps.map.navigator.model.impl.data.Route;

public class RouteForFoundedPlace extends Route {

    public RouteForFoundedPlace() {
        setId("route_for_founded_place");
        setTitle("Route for founded place");
        setOrigin(new FoundedRoute_OriginPlace());
        setDestination(new FoundedRoute_DestinationPlace());
        setLastUsedTime(System.currentTimeMillis());
    }
}
