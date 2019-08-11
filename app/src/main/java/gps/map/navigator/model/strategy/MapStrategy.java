package gps.map.navigator.model.strategy;

import gps.map.navigator.model.impl.common.EmptyMapSdk;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IMapSdk;
import gps.map.navigator.model.interfaces.IMapSetting;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class MapStrategy implements IMapSdk {

    private static final IMapSdk DEFAULT_STRATEGY = new EmptyMapSdk();
    private static IMapSdk activeStrategy;
    private static final MapStrategy instance = new MapStrategy();

    private MapStrategy() {
        activeStrategy = DEFAULT_STRATEGY;
    }

    public static MapStrategy getInstance() {
        return instance;
    }

    public void setStrategy(IMapSdk mapStrategy) {
        if (mapStrategy != null) {
            activeStrategy = mapStrategy;
        } else {
            activeStrategy = DEFAULT_STRATEGY;
        }
    }

    @Override
    public void showMeOnMap(IPlaceListener placeListener) {
        activeStrategy.showMeOnMap(placeListener);
    }

    @Override
    public void setMapSettings(IMapSetting mapSettings) {
        activeStrategy.setMapSettings(mapSettings);
    }

    @Override
    public void showPlace(IMapPlace place, IPlaceShowListener placeShowListener) {
        activeStrategy.showPlace(place, placeShowListener);
    }

    @Override
    public void showRoute(IRoute route, IRouteReadyListener routeListener) {
        activeStrategy.showRoute(route, routeListener);
    }

    @Override
    public void findPlace(IPlaceListener placeListener) {
        activeStrategy.findPlace(placeListener);
    }

    @Override
    public IMapPlace getMyLocation() {
        return activeStrategy.getMyLocation();
    }

    @Override
    public IMapPlace getLastPickedPlace() {
        return activeStrategy.getLastPickedPlace();
    }

    @Override
    public void navigate(IRoute route, IRouteListener routeListener) {

    }
}
