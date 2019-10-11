package gps.map.navigator.model.impl.common;

import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;
import javax.inject.Singleton;

import gps.map.navigator.common.cache.Storage;
import gps.map.navigator.common.utils.SerializationUtils;
import gps.map.navigator.model.impl.data.MapPlace;
import gps.map.navigator.model.impl.data.MapSettingImpl;
import gps.map.navigator.model.impl.data.Route;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.model.interfaces.IRoute;

public class DataCache implements Cache {

    @Inject
    public Storage storage;

    private SerializationUtils<MapPlace> placeSerializationUtils;
    private SerializationUtils<Route> routeSerializationUtils;
    private SerializationUtils<MapSettingImpl> mapSettingSerializationUtils;

    private final String KEY_MY_LOCATION = "key_my_location";
    private final String KEY_LAST_ORIGIN = "key_last_origin";
    private final String KEY_LAST_PLACE = "key_last_place";
    private final String KEY_LAST_DESTINATION = "key_last_destination";
    private final String KEY_LAST_ROUTE = "key_last_route";
    private final String KEY_MAP_SETTINGS = "key_map_settings";

    @Inject
    public DataCache() {
        placeSerializationUtils = new SerializationUtils<>();
        routeSerializationUtils = new SerializationUtils<>();
        mapSettingSerializationUtils = new SerializationUtils<>();
    }

    @Override
    public List<IMapPlace> getHistoryPlaces() {
        List<byte[]> arrays = storage.getChunkedData();
        List<IMapPlace> output = new ArrayList<>(arrays.size());
        for (int i = 0; i < arrays.size(); i++) {
            output.add(placeSerializationUtils.deserialize(arrays.get(i)));
        }
        return output;
    }

    @Override
    public void setHistoryPlaces(List<IMapPlace> historyPlaces) {
        List<byte[]> arrays = new ArrayList<>();
        for (int i = 0; i < historyPlaces.size(); i++) {
            arrays.add(placeSerializationUtils.serialize((MapPlace) historyPlaces.get(i)));
        }
        storage.saveChunkedData(arrays);
    }

    @Override
    public IMapPlace getMyLocation() {
        return getPlace(KEY_MY_LOCATION);
    }

    private IMapPlace getPlace(String key) {
        byte[] data = getRawData(key);
        return placeSerializationUtils.deserialize(data);
    }

    @Override
    public void setMyLocation(IMapPlace myLocation) {
        savePlace(myLocation, KEY_MY_LOCATION);
    }

    private void savePlace(IMapPlace place, String key) {
        byte[] data = placeSerializationUtils.serialize((MapPlace) place);
        setRawData(key, data);
    }

    @Override
    public IMapPlace getLastOrigin() {
        return getPlace(KEY_LAST_ORIGIN);
    }

    @Override
    public void setLastOrigin(IMapPlace lastOrigin) {
        savePlace(lastOrigin, KEY_LAST_ORIGIN);
    }

    @Override
    public IMapPlace getLastDestination() {
        return getPlace(KEY_LAST_DESTINATION);
    }

    @Override
    public void setLastDestination(IMapPlace lastDestination) {
        savePlace(lastDestination, KEY_LAST_DESTINATION);
    }

    @Override
    public IRoute getLastRoute() {
        byte[] data = getRawData(KEY_LAST_ROUTE);
        return routeSerializationUtils.deserialize(data);
    }

    @Override
    public void setLastRoute(IRoute lastRoute) {
        byte[] data = routeSerializationUtils.serialize((Route) lastRoute);
        setRawData(KEY_LAST_ROUTE, data);
    }

    @Override
    public IMapPlace getLastPlace() {
        return getPlace(KEY_LAST_PLACE);
    }

    @Override
    public void setLastPlace(IMapPlace lastPlace) {
        savePlace(lastPlace, KEY_LAST_PLACE);
    }

    @Override
    public MapSetting getMapSettings() {
        byte[] data = getRawData(KEY_MAP_SETTINGS);
        return mapSettingSerializationUtils.deserialize(data);
    }

    @Override
    public void setMapSettings(MapSetting mapSettings) {
        byte[] data = mapSettingSerializationUtils.serialize((MapSettingImpl) mapSettings);
        setRawData(KEY_MAP_SETTINGS, data);
    }

    @Override
    public byte[] getRawData(String key) {
        return storage.getData(key, null);
    }

    @Override
    public void setRawData(String key, byte[] rawData) {
        storage.saveData(key, rawData);
    }
}
