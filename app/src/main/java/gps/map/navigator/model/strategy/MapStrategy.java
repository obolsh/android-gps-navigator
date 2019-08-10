package gps.map.navigator.model.strategy;

import gps.map.navigator.model.MapType;
import gps.map.navigator.model.impl.common.EmptyMap;
import gps.map.navigator.model.interfaces.IMapSetting;

public class MapStrategy implements IMapSetting {

    private static final IMapSetting DEFAULT_STRATEGY = new EmptyMap();
    private static IMapSetting activeStrategy;
    private static final MapStrategy instance = new MapStrategy();

    private MapStrategy() {
        activeStrategy = DEFAULT_STRATEGY;
    }

    public static MapStrategy getInstance() {
        return instance;
    }

    public void setStrategy(IMapSetting mapStrategy) {
        if (mapStrategy != null) {
            activeStrategy = mapStrategy;
        } else {
            activeStrategy = DEFAULT_STRATEGY;
        }
    }

    @Override
    public void setId(String id) {
        activeStrategy.setId(id);
    }

    @Override
    public String getId() {
        return activeStrategy.getId();
    }

    @MapType
    @Override
    public int getMapType() {
        return activeStrategy.getMapType();
    }

    @Override
    public void setMapType(@MapType int mapType) {
        activeStrategy.setMapType(mapType);
    }
}
