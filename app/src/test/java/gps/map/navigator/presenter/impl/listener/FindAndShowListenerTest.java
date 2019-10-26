package gps.map.navigator.presenter.impl.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.view.interfaces.IPlaceShowListener;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.getInternalState;

public class FindAndShowListenerTest {

    private MapSdk mapSdk;
    private Cache cache;
    private IPlaceShowListener placeShowListener;
    private IMapPlace place;

    @Before
    public void setUp() throws Exception {
        mapSdk = mock(MapSdk.class);
        cache = mock(Cache.class);
        placeShowListener = mock(IPlaceShowListener.class);
        place = mock(IMapPlace.class);
    }

    @Test
    public void receive_onPlaceLocated_verify() {
        FindAndShowListener listener = new FindAndShowListener(mapSdk, cache, placeShowListener);

        listener.onPlaceLocated(place);

        verify(cache).setLastPlace(eq(place));
        verify(mapSdk).showPlace(eq(place), eq(placeShowListener));

        boolean mapSdk_cleaned = getInternalState(listener, "mapSdk") == null;
        boolean cache_cleaned = getInternalState(listener, "cache") == null;
        boolean placeShowListener_cleaned = getInternalState(listener, "placeShowListener") == null;

        assertTrue(mapSdk_cleaned && cache_cleaned && placeShowListener_cleaned);
    }

    @Test
    public void receive_onPlaceLocationFailed_verify() {
        FindAndShowListener listener = new FindAndShowListener(mapSdk, cache, placeShowListener);

        listener.onPlaceLocationFailed(new Exception(""));

        verify(placeShowListener).onPlaceShowFailed(any(Exception.class));

        boolean mapSdk_cleaned = getInternalState(listener, "mapSdk") == null;
        boolean cache_cleaned = getInternalState(listener, "cache") == null;
        boolean placeShowListener_cleaned = getInternalState(listener, "placeShowListener") == null;

        assertTrue(mapSdk_cleaned && cache_cleaned && placeShowListener_cleaned);
    }
}