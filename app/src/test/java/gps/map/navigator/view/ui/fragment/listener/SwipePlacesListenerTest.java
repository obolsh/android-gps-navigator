package gps.map.navigator.view.ui.fragment.listener;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class SwipePlacesListenerTest {
    private ISwipeRoute fragment;

    @Before
    public void setUp() throws Exception {
        fragment = mock(ISwipeRoute.class);
    }

    @Test
    public void receive_onClick_verify() {
        SwipePlacesListener listener = new SwipePlacesListener();
        setInternalState(listener, "fragment", fragment);

        listener.onClick(null);

        verify(fragment).swipeOriginAndDestination();
    }
}