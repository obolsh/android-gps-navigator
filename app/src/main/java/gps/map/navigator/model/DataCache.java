package gps.map.navigator.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.common.cache.Storage;
import gps.map.navigator.common.utils.DescendingTimeComparator;
import gps.map.navigator.common.utils.SerializationUtils;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.model.interfaces.IRoute;

public class DataCache implements Cache {

    @Inject
    Storage storage;

    private SerializationUtils<IMapPlace> placeSerializationUtils;
    private SerializationUtils<IRoute> routeSerializationUtils;
    private SerializationUtils<MapSetting> mapSettingSerializationUtils;

    static final String KEY_MY_LOCATION = "key_my_location";
    static final String KEY_LAST_ORIGIN = "key_last_origin";
    static final String KEY_LAST_PLACE = "key_last_place";
    static final String KEY_LAST_DESTINATION = "key_last_destination";
    static final String KEY_LAST_ROUTE = "key_last_route";
    static final String KEY_MAP_SETTINGS = "key_map_settings";

    @Inject
    DataCache() {
        placeSerializationUtils = new SerializationUtils<>();
        routeSerializationUtils = new SerializationUtils<>();
        mapSettingSerializationUtils = new SerializationUtils<>();
    }

    @Nullable
    @Override
    public List<IMapPlace> getHistoryPlaces() {
        List<byte[]> arrays = storage.getChunkedData();
        if (arrays != null) {
            int cacheSize = arrays.size();
            List<IMapPlace> output = new ArrayList<>(cacheSize);
            for (int i = 0; i < cacheSize; i++) {
                output.add(placeSerializationUtils.deserialize(arrays.get(i)));
            }
            return appendMyLocation(output);
        } else {
            return appendMyLocation(new ArrayList<IMapPlace>());
        }
    }

    private List<IMapPlace> appendMyLocation(@NonNull List<IMapPlace> output) {
        IMapPlace myLocation = getMyLocation();
        if (myLocation != null && !placeAlreadyExist(output, myLocation)) {
            output.add(getMyLocation());
        }
        return output;
    }


    @Override
    public void setHistoryPlaces(@Nullable List<IMapPlace> historyPlaces) {
        if (historyPlaces != null) {
            sortByLastUsedTime(historyPlaces);
            List<byte[]> arrays = new ArrayList<>();
            for (int i = 0; i < historyPlaces.size(); i++) {
                arrays.add(placeSerializationUtils.serialize(historyPlaces.get(i)));
            }
            storage.saveChunkedData(arrays);
        }
    }

    private void sortByLastUsedTime(@NonNull List<IMapPlace> places) {
        Collections.sort(places, new DescendingTimeComparator());
    }

    @Nullable
    @Override
    public IMapPlace getMyLocation() {
        return getPlace(KEY_MY_LOCATION);
    }

    @Nullable
    private IMapPlace getPlace(@Nullable String key) {
        if (key != null && !key.isEmpty()) {
            byte[] data = getRawData(key);
            return placeSerializationUtils.deserialize(data);
        } else {
            return null;
        }
    }

    @Override
    public void setMyLocation(@Nullable IMapPlace myLocation) {
        if (myLocation != null) {
            savePlace(myLocation, KEY_MY_LOCATION);
        }
    }

    private void savePlace(@Nullable IMapPlace place, @Nullable String key) {
        if (place != null && key != null && !key.isEmpty()) {
            byte[] data = placeSerializationUtils.serialize(place);
            if (data != null) {
                setRawData(key, data);
            }
            appendPlaceToHistory(place);
        }
    }

    private boolean placeAlreadyExist(@NonNull List<IMapPlace> historyPlaces, @NonNull IMapPlace place) {
        for (int i = 0; i < historyPlaces.size(); i++) {
            if (place.getId().equals(historyPlaces.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

    private void appendPlaceToHistory(@NonNull IMapPlace place) {
        List<IMapPlace> historyPlaces = getHistoryPlaces();
        if (historyPlaces == null) {
            return;
        }
        if (historyPlaces.isEmpty()) {
            historyPlaces.add(place);
        } else if (!placeAlreadyExist(historyPlaces, place)) {
            historyPlaces.add(place);
        }
        setHistoryPlaces(historyPlaces);
    }

    @Nullable
    @Override
    public IMapPlace getLastOrigin() {
        return getPlace(KEY_LAST_ORIGIN);
    }

    @Override
    public void setLastOrigin(@Nullable IMapPlace lastOrigin) {
        if (lastOrigin != null) {
            savePlace(lastOrigin, KEY_LAST_ORIGIN);
        } else {
            setRawData(KEY_LAST_ORIGIN, null);
        }
    }

    @Nullable
    @Override
    public IMapPlace getLastDestination() {
        return getPlace(KEY_LAST_DESTINATION);
    }

    @Override
    public void setLastDestination(@Nullable IMapPlace lastDestination) {
        if (lastDestination != null) {
            savePlace(lastDestination, KEY_LAST_DESTINATION);
        } else {
            setRawData(KEY_LAST_DESTINATION, null);
        }
    }

    @Nullable
    @Override
    public IRoute getLastRoute() {
        byte[] data = getRawData(KEY_LAST_ROUTE);
        if (data != null) {
            return routeSerializationUtils.deserialize(data);
        } else {
            return null;
        }
    }

    @Override
    public void setLastRoute(@Nullable IRoute lastRoute) {
        if (lastRoute != null) {
            byte[] data = routeSerializationUtils.serialize(lastRoute);
            if (data != null) {
                setRawData(KEY_LAST_ROUTE, data);
            }
        } else {
            setRawData(KEY_LAST_ROUTE, null);
        }
    }

    @Nullable
    @Override
    public IMapPlace getLastPlace() {
        return getPlace(KEY_LAST_PLACE);
    }

    @Override
    public void setLastPlace(@Nullable IMapPlace lastPlace) {
        if (lastPlace != null) {
            savePlace(lastPlace, KEY_LAST_PLACE);
        } else {
            setRawData(KEY_LAST_PLACE, null);
        }
    }

    @Nullable
    @Override
    public MapSetting getMapSettings() {
        byte[] data = getRawData(KEY_MAP_SETTINGS);
        if (data != null) {
            return mapSettingSerializationUtils.deserialize(data);
        } else {
            return null;
        }
    }

    @Override
    public void setMapSettings(@Nullable MapSetting mapSettings) {
        if (mapSettings != null) {
            byte[] data = mapSettingSerializationUtils.serialize(mapSettings);
            if (data != null) {
                setRawData(KEY_MAP_SETTINGS, data);
            }
        }
    }

    @Nullable
    @Override
    public byte[] getRawData(@Nullable String key) {
        if (key != null && !key.isEmpty()) {
            return storage.getData(key, null);
        } else {
            return null;
        }
    }

    @Override
    public void setRawData(@Nullable String key, @Nullable byte[] rawData) {
        if (key != null && !key.isEmpty()) {
            storage.saveData(key, rawData);
        }
    }

    @Override
    public void removeHistoryPlace(@Nullable IMapPlace placeToRemove) {
        if (placeToRemove != null) {
            List<IMapPlace> places = getHistoryPlaces();
            if (places != null) {
                int position = getPosition(places, placeToRemove);
                places.remove(position);
                setHistoryPlaces(places);
            }
        }
    }

    @Override
    public void addNewHistoryPlace(@Nullable IMapPlace newPlace) {
        if (newPlace != null) {
            List<IMapPlace> places = getHistoryPlaces();
            if (places != null && !placeAlreadyExist(places, newPlace)) {
                places.add(newPlace);
                setHistoryPlaces(places);
            }
        }
    }

    private int getPosition(@NonNull List<IMapPlace> places, @NonNull IMapPlace item) {
        for (int i = 0; i < places.size(); i++) {
            if (item.getId().equals(places.get(i).getId())) {
                return i;
            }
        }
        return 0;
    }
}
