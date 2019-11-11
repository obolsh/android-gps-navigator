package gps.map.navigator.presenter.impl.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceListener;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.getInternalState;

public class FindPlaceListenerTest {

    private Presenter cache;
    private IPlaceListener placeListener;
    private IMapPlace place;

    @Before
    public void setUp() throws Exception {
        cache = mock(Presenter.class);
        placeListener = mock(IPlaceListener.class);
        place = mock(IMapPlace.class);
    }

    @Test
    public void receive_onPlaceLocated_verify() {
        FindPlaceListener listener = new FindPlaceListener(cache, placeListener);

        listener.onPlaceLocated(place);

        verify(cache).setLastPlace(eq(place));
        verify(placeListener).onPlaceLocated(eq(place));

        boolean placeListener_cleaned = getInternalState(listener, "listener") == null;
        boolean cache_cleaned = getInternalState(listener, "cache") == null;

        assertTrue(placeListener_cleaned && cache_cleaned);
    }

    @Test
    public void receive_onPlaceLocationFailed_verify() {
        FindPlaceListener listener = new FindPlaceListener(cache, placeListener);

        listener.onPlaceLocationFailed(new Exception(""));

        verify(placeListener).onPlaceLocationFailed(any(Exception.class));

        boolean placeListener_cleaned = getInternalState(listener, "listener") == null;
        boolean cache_cleaned = getInternalState(listener, "cache") == null;

        assertTrue(placeListener_cleaned && cache_cleaned);
    }
}