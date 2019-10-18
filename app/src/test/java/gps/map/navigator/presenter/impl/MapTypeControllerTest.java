package gps.map.navigator.presenter.impl;

import org.junit.Before;
import org.junit.Test;


import gps.map.navigator.model.MapType;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;


public class MapTypeControllerTest {

    private MapSetting mapSetting;
    private Cache cache;
    private MapSdk mapSdk;

    @Before
    public void setUp() throws Exception {
        mapSdk = mock(MapSdk.class);
        cache = mock(Cache.class);
        mapSetting = mock(MapSetting.class);

        when(cache.getMapSettings()).thenReturn(mapSetting);
    }

    private void setReferences(MapTypeController controller) {
        setInternalState(controller, "mapSdk", mapSdk);
        setInternalState(controller, "cache", cache);
        setInternalState(controller, "mapSetting", mapSetting);
    }

    @Test
    public void make_enableTraffic_day_satelite_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_DAY);

        controller.enableTraffic(true);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_TRAFFIC_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableTraffic_day_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_DAY);

        controller.enableTraffic(true);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_TRAFFIC_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableTraffic_night_satelite_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_NIGHT);

        controller.enableTraffic(true);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_TRAFFIC_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableTraffic_night_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_NIGHT);

        controller.enableTraffic(true);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_TRAFFIC_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableTraffic_day_satelite_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_TRAFFIC_DAY);

        controller.enableTraffic(false);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableTraffic_day_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_TRAFFIC_DAY);

        controller.enableTraffic(false);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableTraffic_night_satelite_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_TRAFFIC_NIGHT);

        controller.enableTraffic(false);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableTraffic_night_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_TRAFFIC_NIGHT);

        controller.enableTraffic(false);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableNightMode_traffic_satelite_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_TRAFFIC_DAY);

        controller.enableNightMode(true);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_TRAFFIC_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableNightMode_traffic_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_TRAFFIC_DAY);

        controller.enableNightMode(true);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_TRAFFIC_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableNightMode_non_traffic_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_DAY);

        controller.enableNightMode(true);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableNightMode_non_traffic_satelite_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_DAY);

        controller.enableNightMode(true);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }


    @Test
    public void make_disableNightMode_traffic_satelite_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_TRAFFIC_NIGHT);

        controller.enableNightMode(false);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_TRAFFIC_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableNightMode_traffic_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_TRAFFIC_NIGHT);

        controller.enableNightMode(false);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_TRAFFIC_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableNightMode_non_traffic_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_NIGHT);

        controller.enableNightMode(false);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableNightMode_non_traffic_satelite_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_NIGHT);

        controller.enableNightMode(false);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableSatelite_day_traffic_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_TRAFFIC_DAY);

        controller.enableSatelite(true);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_TRAFFIC_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableSatelite_day_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_DAY);

        controller.enableSatelite(true);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableSatelite_night_traffic_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_TRAFFIC_NIGHT);

        controller.enableSatelite(true);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_TRAFFIC_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_enableSatelite_night_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.NORMAL_NIGHT);

        controller.enableSatelite(true);

        verify(mapSetting).setMapType(eq(MapType.SATELLITE_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableSatelite_day_traffic_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_TRAFFIC_DAY);

        controller.enableSatelite(false);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_TRAFFIC_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_diableSatelite_day_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_DAY);

        controller.enableSatelite(false);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_DAY));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableSatelite_night_traffic_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_TRAFFIC_NIGHT);

        controller.enableSatelite(false);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_TRAFFIC_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }

    @Test
    public void make_disableSatelite_night_normal_verify() {
        MapTypeController controller = new MapTypeController();
        setReferences(controller);
        when(mapSetting.getMapType()).thenReturn(MapType.SATELLITE_NIGHT);

        controller.enableSatelite(false);

        verify(mapSetting).setMapType(eq(MapType.NORMAL_NIGHT));
        verify(cache).setMapSettings(eq(mapSetting));
        verify(mapSdk).setMapSettings(eq(mapSetting));
    }
}