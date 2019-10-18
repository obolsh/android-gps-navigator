package gps.map.navigator.presenter.impl;

import javax.inject.Inject;

import gps.map.navigator.model.MapType;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.presenter.interfaces.IMapTypeController;

public class MapTypeController implements IMapTypeController {

    @Inject
    MapSetting mapSetting;
    @Inject
    Cache cache;
    @Inject
    MapSdk mapSdk;

    @Inject
    MapTypeController() {
    }

    @Override
    public void enableTraffic(boolean enable) {
        MapSetting mapSetting = getMapSettings();
        boolean isDay = isDay(mapSetting);
        if (enable) {
            mapSetting.setMapType(isDay ? MapType.TRAFFIC_DAY : MapType.TRAFFIC_NIGHT);
        } else if (isDefaultMap(mapSetting)) {
            mapSetting.setMapType(isDay ? MapType.NORMAL_DAY : MapType.NORMAL_NIGHT);
        } else {
            mapSetting.setMapType(isDay ? MapType.SATELLITE_DAY : MapType.SATELLITE_NIGHT);
        }
        saveNewMapSettings(mapSetting);
    }

    private MapSetting getMapSettings() {
        if (hasCachedMapSettings()) {
            return cache.getMapSettings();
        } else {
            return mapSetting;
        }
    }

    private boolean hasCachedMapSettings() {
        return cache != null && cache.getMapSettings() != null;
    }

    private boolean isDay(MapSetting mapSetting) {
        int mapType = mapSetting.getMapType();
        return mapType == MapType.NORMAL_DAY
                || mapType == MapType.TRAFFIC_DAY
                || mapType == MapType.SATELLITE_DAY;
    }

    private void saveNewMapSettings(MapSetting mapSetting) {
        if (mapSdk != null && mapSetting != null) {
            mapSdk.setMapSettings(mapSetting);
        }

        if (cache != null && mapSetting != null) {
            cache.setMapSettings(mapSetting);
        }
    }

    private boolean isDefaultMap(MapSetting mapSetting) {
        int mapType = mapSetting.getMapType();
        return mapType == MapType.NORMAL_DAY
                || mapType == MapType.NORMAL_NIGHT;
    }

    @Override
    public void enableNightMode(boolean enable) {
        MapSetting mapSetting = getMapSettings();
        boolean isDefaultMap = isDefaultMap(mapSetting);
        boolean isTrafficMap = isTrafficMap(mapSetting);
        if (enable) {
            mapSetting.setMapType(
                    isDefaultMap ? MapType.TRAFFIC_NIGHT :
                            isTrafficMap ? MapType.TRAFFIC_NIGHT : MapType.SATELLITE_NIGHT);
        } else {
            mapSetting.setMapType(
                    isDefaultMap ? MapType.TRAFFIC_DAY :
                            isTrafficMap ? MapType.TRAFFIC_DAY : MapType.SATELLITE_DAY);
        }
        saveNewMapSettings(mapSetting);
    }


    private boolean isTrafficMap(MapSetting mapSetting) {
        int mapType = mapSetting.getMapType();
        return mapType == MapType.TRAFFIC_DAY
                || mapType == MapType.TRAFFIC_NIGHT;
    }
}
