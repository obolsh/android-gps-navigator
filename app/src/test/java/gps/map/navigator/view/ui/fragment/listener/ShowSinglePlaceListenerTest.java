package gps.map.navigator.view.ui.fragment.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class ShowSinglePlaceListenerTest {
    private AbstractAdapter adapter;
    private IMapPlace mapPlace;

    @Before
    public void setUp() throws Exception {
        adapter = mock(AbstractAdapter.class);
        mapPlace = mock(IMapPlace.class);
    }

    @Test
    public void receive_onPlaceLocated_verify() {
        ShowSinglePlaceListener listener = new ShowSinglePlaceListener();
        setInternalState(listener, "adapter", adapter);

        listener.onPlaceLocated(mapPlace);

        verify(adapter).showSinglePlace(eq(mapPlace));
    }
}