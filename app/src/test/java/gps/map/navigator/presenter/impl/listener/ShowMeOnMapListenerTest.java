package gps.map.navigator.presenter.impl.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.interfaces.IPlaceListener;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.getInternalState;

public class ShowMeOnMapListenerTest {

    private Cache cache;
    private IPlaceListener placeListener;
    private IMapPlace place;

    @Before
    public void setUp() throws Exception {
        cache = mock(Cache.class);
        placeListener = mock(IPlaceListener.class);
        place = mock(IMapPlace.class);
    }

    @Test
    public void receive_onPlaceLocated_verify() {
        ShowMeOnMapListener listener = new ShowMeOnMapListener(cache, placeListener);

        listener.onPlaceLocated(place);

        verify(cache).setMyLocation(eq(place));
        verify(placeListener).onPlaceLocated(eq(place));

        boolean placeListener_cleaned = getInternalState(listener, "placeListener") == null;
        boolean cache_cleaned = getInternalState(listener, "cache") == null;

        assertTrue(placeListener_cleaned && cache_cleaned);
    }

    @Test
    public void receive_onPlaceLocationFailed_verify() {
        ShowMeOnMapListener listener = new ShowMeOnMapListener(cache, placeListener);

        listener.onPlaceLocationFailed(new Exception(""));

        verify(placeListener).onPlaceLocationFailed(any(Exception.class));

        boolean placeListener_cleaned = getInternalState(listener, "placeListener") == null;
        boolean cache_cleaned = getInternalState(listener, "cache") == null;

        assertTrue(placeListener_cleaned && cache_cleaned);
    }
}