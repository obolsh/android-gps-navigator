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
        MapSetting setting = getMapSettings();
        setting.setMapType(getMapTypeForTraffic(setting, enable));
        saveNewMapSettings(setting);
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

    private boolean isDay(MapSetting setting) {
        return hasDayStamp(setting.getMapType());
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

    private void saveNewMapSettings(MapSetting setting) {
        if (setting != null) {
            mapSdk.setMapSettings(setting);
            cache.setMapSettings(setting);
        }
    }

    @Override
    public void enableSatellite(boolean enable) {
        MapSetting setting = getMapSettings();
        setting.setMapType(getMapTypeForSatelite(setting, enable));
        saveNewMapSettings(setting);
    }

    private int getMapTypeForSatelite(MapSetting setting, boolean enable) {
        boolean isDay = isDay(setting);
        boolean isTrafficMap = isTrafficMap(setting);
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

    private boolean isTrafficMap(MapSetting setting) {
        return hasTrafficStamp(setting.getMapType());
    }

    private boolean hasTrafficStamp(int mapType) {
        return mapType == MapType.NORMAL_TRAFFIC_DAY
                || mapType == MapType.NORMAL_TRAFFIC_NIGHT
                || mapType == MapType.SATELLITE_TRAFFIC_DAY
                || mapType == MapType.SATELLITE_TRAFFIC_NIGHT;
    }

    @Override
    public void enableNightMode(boolean enable) {
        MapSetting setting = getMapSettings();
        setting.setMapType(getMapTypeForNightMode(setting, enable));
        saveNewMapSettings(setting);
    }

    @Override
    public boolean hasTrafficMode() {
        MapSetting setting = getMapSettings();
        return isTrafficMap(setting);
    }

    @Override
    public boolean hasSatelliteMode() {
        MapSetting setting = getMapSettings();
        return isSateliteMap(setting);
    }

    @Override
    public boolean hasNightMode() {
        MapSetting setting = getMapSettings();
        return !isDay(setting);
    }

    private int getMapTypeForNightMode(MapSetting setting, boolean enable) {
        boolean isSateliteMap = isSateliteMap(setting);
        boolean isTrafficMap = isTrafficMap(setting);
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
