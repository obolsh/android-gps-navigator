package gps.map.navigator.view.ui.fragment.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class DestinationChangeListenerTest {
    private ISwipeRoute swipeRoute;
    private IMapPlace mapPlace;

    @Before
    public void setUp() throws Exception {
        swipeRoute = mock(ISwipeRoute.class);
        mapPlace = mock(IMapPlace.class);
    }

    @Test
    public void receive_onClick_verify() {
        DestinationChangeListener listener = new DestinationChangeListener();
        setInternalState(listener, "swipeRoute", swipeRoute);

        listener.onPlaceLocated(mapPlace);

        verify(swipeRoute).setOnlyDestination(eq(mapPlace));
    }
}