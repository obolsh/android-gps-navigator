package gps.navigator.mapboxsdk;

public class MapSdkProvider {

    private static MapSdkProvider instance;
    private MapSdkInstance mapSdkInstance;

    private MapSdkProvider() {
    }

    public static MapSdkProvider getInstance() {
        if (instance == null) {
            instance = new MapSdkProvider();
        }
        return instance;
    }

    public MapSdkInstance getMapSdkInstance() {
        return mapSdkInstance;
    }

    public void setMapSdkInstance(MapSdkInstance mapSdkInstance) {
        this.mapSdkInstance = mapSdkInstance;
    }
}
