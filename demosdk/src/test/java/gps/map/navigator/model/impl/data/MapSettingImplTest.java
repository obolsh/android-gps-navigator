package gps.map.navigator.model.impl.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapSettingImplTest {

    @Test
    public void make_getY_verify() {
        MapSettingImpl mapSetting = new MapSettingImpl();
        mapSetting.setId("foo");

        assertEquals("foo", mapSetting.getId());
    }

    @Test
    public void make_getMapType_verify() {
        MapSettingImpl mapSetting = new MapSettingImpl();
        mapSetting.setMapType(123);

        assertEquals(123, mapSetting.getMapType());
    }

    @Test
    public void make_getY_verify_constructor() {
        MapSettingImpl mapSetting = new MapSettingImpl("foo", 0);

        assertEquals("foo", mapSetting.getId());
    }

    @Test
    public void make_getMapType_verify_constructor() {
        MapSettingImpl mapSetting = new MapSettingImpl("", 123);

        assertEquals(123, mapSetting.getMapType());
    }
}