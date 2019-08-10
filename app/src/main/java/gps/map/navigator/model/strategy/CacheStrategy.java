package gps.map.navigator.model.strategy;

import java.util.List;

import gps.map.navigator.model.impl.common.EmptyCache;
import gps.map.navigator.model.interfaces.ICache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IMapSetting;
import gps.map.navigator.model.interfaces.IRoute;

public class CacheStrategy implements ICache {

    private static final ICache DEFAULT_STRATEGY = new EmptyCache();
    private static ICache activeStrategy;
    private static final CacheStrategy instance = new CacheStrategy();

    private CacheStrategy() {
        activeStrategy = DEFAULT_STRATEGY;
    }

    public static CacheStrategy getInstance() {
        return instance;
    }

    public void setStrategy(ICache cacheStrategy) {
        if (cacheStrategy != null) {
            activeStrategy = cacheStrategy;
        } else {
            activeStrategy = DEFAULT_STRATEGY;
        }
    }

    @Override
    public List<IMapPlace> getHistoryPlaces() {
        return activeStrategy.getHistoryPlaces();
    }

    @Override
    public void setHistoryPlaces(List<IMapPlace> historyPlaces) {
        activeStrategy.setHistoryPlaces(historyPlaces);
    }

    @Override
    public IMapPlace getMyLocation() {
        return activeStrategy.getMyLocation();
    }

    @Override
    public void setMyLocation(IMapPlace myLocation) {
        activeStrategy.setMyLocation(myLocation);
    }

    @Override
    public IMapPlace getLastOrigin() {
        return activeStrategy.getLastOrigin();
    }

    @Override
    public void setLastOrigin(IMapPlace lastOrigin) {
        activeStrategy.setLastOrigin(lastOrigin);
    }

    @Override
    public IMapPlace getLastDestination() {
        return activeStrategy.getLastDestination();
    }

    @Override
    public void setLastDestination(IMapPlace lastDestination) {
        activeStrategy.setLastDestination(lastDestination);
    }

    @Override
    public IRoute getLastRoute() {
        return activeStrategy.getLastRoute();
    }

    @Override
    public void setLastRoute(IRoute lastRoute) {
        activeStrategy.setLastRoute(lastRoute);
    }

    @Override
    public IMapPlace getLastPlace() {
        return activeStrategy.getLastPlace();
    }

    @Override
    public void setLastPlace(IMapPlace lastPlace) {
        activeStrategy.setLastPlace(lastPlace);
    }

    @Override
    public IMapSetting getMapSettings() {
        return activeStrategy.getMapSettings();
    }

    @Override
    public void setMapSettings(IMapSetting mapSettings) {
        activeStrategy.setMapSettings(mapSettings);
    }

    @Override
    public byte[] getRawData(String key) {
        return activeStrategy.getRawData(key);
    }

    @Override
    public void setRawData(String key, byte[] rawData) {
        activeStrategy.setRawData(key, rawData);
    }
}
