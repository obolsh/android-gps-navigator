package gps.navigator.mapboxsdk;

public class MapRouteProvider {

    private static MapRouteProvider instance;
    private MapRouteInstance routeInstance;

    private MapRouteProvider() {
    }

    public static MapRouteProvider getInstance() {
        if (instance == null) {
            instance = new MapRouteProvider();
        }
        return instance;
    }

    public MapRouteInstance getMapRouteInstance() {
        return routeInstance;
    }

    public void setMapRouteInstance(MapRouteInstance instance) {
        this.routeInstance = instance;
    }
}
