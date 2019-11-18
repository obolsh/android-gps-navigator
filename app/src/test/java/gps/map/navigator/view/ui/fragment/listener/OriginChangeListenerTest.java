package gps.map.navigator.view.ui.fragment.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

public class OriginChangeListenerTest {
    private ISwipeRoute swipeRoute;
    private IMapPlace mapPlace;
    private Cache cache;

    @Before
    public void setUp() throws Exception {
        swipeRoute = mock(ISwipeRoute.class);
        mapPlace = mock(IMapPlace.class);
        cache = mock(Cache.class);
    }

    @Test
    public void receive_onPlaceLocated_same_as_destination_verify() {
        OriginChangeListener listener = new OriginChangeListener();
        setInternalState(listener, "swipeRoute", swipeRoute);
        setInternalState(listener, "cache", cache);
        when(cache.getLastDestination()).thenReturn(mapPlace);

        listener.onPlaceLocated(mapPlace);

        verify(swipeRoute, times(0)).setOnlyOrigin(eq(mapPlace));
    }

    @Test
    public void receive_onPlaceLocated_not_same_as_destination_verify() {
        OriginChangeListener listener = new OriginChangeListener();
        setInternalState(listener, "swipeRoute", swipeRoute);
        setInternalState(listener, "cache", cache);
        IMapPlace place = mock(IMapPlace.class);
        when(place.getLatitude()).thenReturn(12d);
        when(place.getLongitude()).thenReturn(12d);
        when(cache.getLastDestination()).thenReturn(place);

        listener.onPlaceLocated(mapPlace);

        verify(swipeRoute).setOnlyOrigin(eq(mapPlace));
    }
}