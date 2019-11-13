package gps.navigator.mapboxsdk;

public class MapViewProvider {

    private static MapViewProvider instance;
    private MapViewInstance mapViewInstance;

    public static MapViewProvider getInstance() {
        if (instance == null) {
            instance = new MapViewProvider();
        }
        return instance;
    }

    public MapViewInstance getMapViewInstance() {
        return mapViewInstance;
    }

    public void setMapViewInstance(MapViewInstance mapViewInstance) {
        this.mapViewInstance = mapViewInstance;
    }
}
