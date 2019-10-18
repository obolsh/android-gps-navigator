package gps.map.navigator.presenter.impl;

import org.junit.Before;
import org.junit.Test;


import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MapTypeControllerTest {

    MapSetting mapSetting;
    Cache cache;
    MapSdk mapSdk;

    @Before
    public void setUp() throws Exception {
        mapSdk = mock(MapSdk.class);
        cache = mock(Cache.class);
        mapSetting = mock(MapSetting.class);

        when(cache.getMapSettings()).thenReturn(mapSetting);
    }

    @Test
    public void enableTraffic() {
    }

    @Test
    public void enableNightMode() {
    }
}