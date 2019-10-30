package gps.map.navigator.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.common.cache.Storage;
import gps.map.navigator.common.utils.SerializationUtils;
import gps.map.navigator.model.DataCache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.model.interfaces.MapSetting;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

public class DataCacheTest {

    private Storage storage;
    private SerializationUtils<IMapPlace> placeSerializationUtils;
    private SerializationUtils<IRoute> routeSerializationUtils;
    private SerializationUtils<MapSetting> mapSettingSerializationUtils;
    private IMapPlace mapPlace;
    private IRoute route;
    private MapSetting mapSetting;

    @Before
    public void setUp() throws Exception {
        storage = mock(Storage.class);

        placeSerializationUtils = mock(SerializationUtils.class);
        routeSerializationUtils = mock(SerializationUtils.class);
        mapSettingSerializationUtils = mock(SerializationUtils.class);

        mapPlace = mock(IMapPlace.class);
        route = mock(IRoute.class);
        mapSetting = mock(MapSetting.class);
    }

    private void setUtils(DataCache cache) {
        setInternalState(cache, "storage", storage);
        setInternalState(cache, "placeSerializationUtils", placeSerializationUtils);
        setInternalState(cache, "routeSerializationUtils", routeSerializationUtils);
        setInternalState(cache, "mapSettingSerializationUtils", mapSettingSerializationUtils);
    }

    private DataCache getCache() {
        DataCache cache = new DataCache();
        setUtils(cache);
        return cache;
    }

    private List<byte[]> getChunckedData() {
        List<byte[]> data = new ArrayList<>();
        data.add("foo".getBytes());
        data.add("baa".getBytes());
        return data;
    }

    @Test
    public void make_getHistoryPlaces_check_empty() {
        DataCache cache = getCache();

        boolean isEmpty = cache.getHistoryPlaces().isEmpty();

        assertTrue(isEmpty);
    }

    @Test
    public void make_getHistoryPlaces_check_size() {
        when(storage.getChunkedData()).thenReturn(getChunckedData());
        when(placeSerializationUtils.deserialize(any(byte[].class))).thenReturn(mapPlace);
        DataCache cache = getCache();

        boolean has_items_in_cache = cache.getHistoryPlaces().size() == 2;
        boolean items_match = cache.getHistoryPlaces().get(1) != null;

        assertTrue(has_items_in_cache && items_match);
    }

    @Test
    public void make_saveHistoryPlaces_verify_null() {
        DataCache cache = getCache();

        cache.setHistoryPlaces(null);

        verify(storage, times(0)).saveChunkedData(any(List.class));
    }

    @Test
    public void make_saveHistoryPlaces_verify_saved() {
        when(placeSerializationUtils.serialize(any(IMapPlace.class))).thenReturn("foo".getBytes());
        DataCache cache = getCache();

        List<IMapPlace> places = new ArrayList<>();
        places.add(mapPlace);

        cache.setHistoryPlaces(places);

        verify(placeSerializationUtils).serialize(eq(mapPlace));
        verify(storage).saveChunkedData(any(List.class));
    }

    @Test
    public void make_getMyLocation_verify_missing() {
        DataCache cache = getCache();

        IMapPlace place = cache.getMyLocation();

        assertNull(place);
    }

    @Test
    public void make_getMyLocation_verify_present() {
        byte[] locationData = "goo".getBytes();
        when(storage.getData(eq(DataCache.KEY_MY_LOCATION), nullable(byte[].class))).thenReturn(locationData);
        when(placeSerializationUtils.deserialize(eq(locationData))).thenReturn(mapPlace);
        DataCache cache = getCache();

        IMapPlace place = cache.getMyLocation();

        assertSame(mapPlace, place);
    }

    @Test
    public void make_setMyLocation_verify() {
        when(placeSerializationUtils.serialize(eq(mapPlace))).thenReturn("fgg".getBytes());
        DataCache cache = getCache();

        cache.setMyLocation(mapPlace);

        verify(placeSerializationUtils, times(2)).serialize(eq(mapPlace));
        verify(storage).saveData(eq(DataCache.KEY_MY_LOCATION), any(byte[].class));
        verify(storage).saveChunkedData(any(List.class));
    }

    @Test
    public void make_getLastOrigin_verify_missing() {
        DataCache cache = getCache();

        IMapPlace place = cache.getLastOrigin();

        assertNull(place);
    }

    @Test
    public void make_getLastOrigin_verify_present() {
        byte[] locationData = "goo".getBytes();
        when(storage.getData(eq(DataCache.KEY_LAST_ORIGIN), nullable(byte[].class))).thenReturn(locationData);
        when(placeSerializationUtils.deserialize(eq(locationData))).thenReturn(mapPlace);
        DataCache cache = getCache();

        IMapPlace place = cache.getLastOrigin();

        assertSame(mapPlace, place);
    }

    @Test
    public void make_setLastOrigin_verify() {
        when(placeSerializationUtils.serialize(eq(mapPlace))).thenReturn("tgg".getBytes());
        DataCache cache = getCache();

        cache.setLastOrigin(mapPlace);

        verify(placeSerializationUtils, times(2)).serialize(eq(mapPlace));
        verify(storage).saveData(eq(DataCache.KEY_LAST_ORIGIN), any(byte[].class));
        verify(storage).saveChunkedData(any(List.class));
    }

    @Test
    public void make_getLastDestination_verify_missing() {
        DataCache cache = getCache();

        IMapPlace place = cache.getLastDestination();

        assertNull(place);
    }

    @Test
    public void make_getLastDestination_verify_present() {
        byte[] locationData = "goo".getBytes();
        when(storage.getData(eq(DataCache.KEY_LAST_DESTINATION), nullable(byte[].class))).thenReturn(locationData);
        when(placeSerializationUtils.deserialize(eq(locationData))).thenReturn(mapPlace);
        DataCache cache = getCache();

        IMapPlace place = cache.getLastDestination();

        assertSame(mapPlace, place);
    }

    @Test
    public void make_setLastDestination_verify() {
        when(placeSerializationUtils.serialize(eq(mapPlace))).thenReturn("tgg".getBytes());
        DataCache cache = getCache();

        cache.setLastDestination(mapPlace);

        verify(placeSerializationUtils, times(2)).serialize(eq(mapPlace));
        verify(storage).saveData(eq(DataCache.KEY_LAST_DESTINATION), any(byte[].class));
        verify(storage).saveChunkedData(any(List.class));
    }

    @Test
    public void make_getLastRoute_verify_missing() {
        DataCache cache = getCache();

        IRoute route = cache.getLastRoute();

        assertNull(route);
    }

    @Test
    public void make_getLastRoute_verify_present() {
        byte[] locationData = "goo".getBytes();
        when(storage.getData(eq(DataCache.KEY_LAST_ROUTE), any(byte[].class))).thenReturn(locationData);
        when(routeSerializationUtils.deserialize(eq(locationData))).thenReturn(route);
        DataCache cache = getCache();

        IRoute route = cache.getLastRoute();

        assertSame(route, route);
    }

    @Test
    public void make_setLastRoute_verify() {
        when(routeSerializationUtils.serialize(eq(route))).thenReturn("fqq".getBytes());
        DataCache cache = getCache();

        cache.setLastRoute(route);

        verify(routeSerializationUtils).serialize(eq(route));
        verify(storage).saveData(eq(DataCache.KEY_LAST_ROUTE), any(byte[].class));
    }

    @Test
    public void make_getLastPlace_verify_missing() {
        DataCache cache = getCache();

        IMapPlace place = cache.getLastPlace();

        assertNull(place);
    }

    @Test
    public void make_getLastPlace_verify_present() {
        byte[] locationData = "goo".getBytes();
        when(storage.getData(eq(DataCache.KEY_LAST_PLACE), nullable(byte[].class))).thenReturn(locationData);
        when(placeSerializationUtils.deserialize(eq(locationData))).thenReturn(mapPlace);
        DataCache cache = getCache();

        IMapPlace place = cache.getLastPlace();

        assertSame(mapPlace, place);
    }

    @Test
    public void make_setLastPlace_verify() {
        when(placeSerializationUtils.serialize(eq(mapPlace))).thenReturn("gtt".getBytes());
        DataCache cache = getCache();

        cache.setLastPlace(mapPlace);

        verify(placeSerializationUtils, times(2)).serialize(eq(mapPlace));
        verify(storage).saveData(eq(DataCache.KEY_LAST_PLACE), any(byte[].class));
        verify(storage).saveChunkedData(any(List.class));
    }

    @Test
    public void make_getMapSettings_verify_missing() {
        DataCache cache = getCache();

        MapSetting setting = cache.getMapSettings();

        assertNull(setting);
    }

    @Test
    public void make_getMapSettings_verify_present() {
        byte[] settingData = "goo".getBytes();
        when(storage.getData(eq(DataCache.KEY_MAP_SETTINGS), nullable(byte[].class))).thenReturn(settingData);
        when(mapSettingSerializationUtils.deserialize(eq(settingData))).thenReturn(mapSetting);
        DataCache cache = getCache();

        MapSetting setting = cache.getMapSettings();

        assertSame(mapSetting, setting);
    }

    @Test
    public void make_setMapSettings_verify() {
        when(mapSettingSerializationUtils.serialize(eq(mapSetting))).thenReturn("yuu".getBytes());
        DataCache cache = getCache();

        cache.setMapSettings(mapSetting);

        verify(mapSettingSerializationUtils).serialize(eq(mapSetting));
        verify(storage).saveData(eq(DataCache.KEY_MAP_SETTINGS), any(byte[].class));
    }

    @Test
    public void make_getRawData_verify_missing() {
        DataCache cache = getCache();

        byte[] data = cache.getRawData(null);

        assertNull(data);
    }

    @Test
    public void make_getRawData_verify_present() {
        byte[] settingData = "goo".getBytes();
        when(storage.getData(eq(DataCache.KEY_MAP_SETTINGS), nullable(byte[].class))).thenReturn(settingData);
        DataCache cache = getCache();

        byte[] data = cache.getRawData(DataCache.KEY_MAP_SETTINGS);

        assertSame(settingData, data);
    }

    @Test
    public void make_setRawData_verify() {
        byte[] settingData = "ftt".getBytes();
        DataCache cache = getCache();

        cache.setRawData(DataCache.KEY_MAP_SETTINGS, settingData);

        verify(storage).saveData(eq(DataCache.KEY_MAP_SETTINGS), eq(settingData));
    }
}