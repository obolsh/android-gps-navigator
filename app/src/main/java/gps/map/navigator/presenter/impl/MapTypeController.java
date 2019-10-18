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
        mapSetting.setMapType(getMapTypeForTraffic(mapSetting, enable));
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
        return cache.getMapSettings() != null;
    }

    private int getMapTypeForTraffic(MapSetting mapSetting, boolean enable) {
        boolean isDay = isDay(mapSetting);
        boolean isSateliteMap = isSateliteMap(mapSetting);
        if (enable) {
            if (isSateliteMap) {
                return isDay ? MapType.SATELLITE_TRAFFIC_DAY : MapType.SATELLITE_TRAFFIC_NIGHT;
            } else {
                return isDay ? MapType.NORMAL_TRAFFIC_DAY : MapType.NORMAL_TRAFFIC_NIGHT;
            }
        } else {
            if (isSateliteMap) {
                return isDay ? MapType.SATELLITE_DAY : MapType.SATELLITE_NIGHT;
            } else {
                return isDay ? MapType.NORMAL_DAY : MapType.NORMAL_NIGHT;
            }
        }
    }

    private boolean isDay(MapSetting mapSetting) {
        return hasDayStamp(mapSetting.getMapType());
    }

    private boolean hasDayStamp(int mapType) {
        return mapType == MapType.NORMAL_DAY
                || mapType == MapType.NORMAL_TRAFFIC_DAY
                || mapType == MapType.SATELLITE_DAY
                || mapType == MapType.SATELLITE_TRAFFIC_DAY;
    }

    private boolean isSateliteMap(MapSetting mapSetting) {
        return hasSateliteStamp(mapSetting.getMapType());
    }

    private boolean hasSateliteStamp(int mapType) {
        return mapType == MapType.SATELLITE_DAY
                || mapType == MapType.SATELLITE_NIGHT
                || mapType == MapType.SATELLITE_TRAFFIC_DAY
                || mapType == MapType.SATELLITE_TRAFFIC_NIGHT;
    }

    private void saveNewMapSettings(MapSetting mapSetting) {
        if (mapSetting != null) {
            mapSdk.setMapSettings(mapSetting);
            cache.setMapSettings(mapSetting);
        }
    }

    @Override
    public void enableSatelite(boolean enable) {
        MapSetting mapSetting = getMapSettings();
        mapSetting.setMapType(getMapTypeForSatelite(mapSetting, enable));
        saveNewMapSettings(mapSetting);
    }

    private int getMapTypeForSatelite(MapSetting mapSetting, boolean enable) {
        boolean isDay = isDay(mapSetting);
        boolean isTrafficMap = isTrafficMap(mapSetting);
        if (enable) {
            if (isTrafficMap) {
                return isDay ? MapType.SATELLITE_TRAFFIC_DAY : MapType.SATELLITE_TRAFFIC_NIGHT;
            } else {
                return isDay ? MapType.SATELLITE_DAY : MapType.SATELLITE_NIGHT;
            }
        } else {
            if (isTrafficMap) {
                return isDay ? MapType.NORMAL_TRAFFIC_DAY : MapType.NORMAL_TRAFFIC_NIGHT;
            } else {
                return isDay ? MapType.NORMAL_DAY : MapType.NORMAL_NIGHT;
            }
        }
    }

    private boolean isTrafficMap(MapSetting mapSetting) {
        return hasTrafficStamp(mapSetting.getMapType());
    }

    private boolean hasTrafficStamp(int mapType) {
        return mapType == MapType.NORMAL_TRAFFIC_DAY
                || mapType == MapType.NORMAL_TRAFFIC_NIGHT
                || mapType == MapType.SATELLITE_TRAFFIC_DAY
                || mapType == MapType.SATELLITE_TRAFFIC_NIGHT;
    }

    @Override
    public void enableNightMode(boolean enable) {
        MapSetting mapSetting = getMapSettings();
        mapSetting.setMapType(getMapTypeForNightMode(mapSetting, enable));
        saveNewMapSettings(mapSetting);
    }

    private int getMapTypeForNightMode(MapSetting mapSetting, boolean enable) {
        boolean isSateliteMap = isSateliteMap(mapSetting);
        boolean isTrafficMap = isTrafficMap(mapSetting);
        if (enable) {
            if (isTrafficMap) {
                return isSateliteMap ? MapType.SATELLITE_TRAFFIC_NIGHT : MapType.NORMAL_TRAFFIC_NIGHT;
            } else {
                return isSateliteMap ? MapType.SATELLITE_NIGHT : MapType.NORMAL_NIGHT;
            }
        } else {
            if (isTrafficMap) {
                return isSateliteMap ? MapType.SATELLITE_TRAFFIC_DAY : MapType.NORMAL_TRAFFIC_DAY;
            } else {
                return isSateliteMap ? MapType.SATELLITE_DAY : MapType.NORMAL_DAY;
            }
        }
    }
}
