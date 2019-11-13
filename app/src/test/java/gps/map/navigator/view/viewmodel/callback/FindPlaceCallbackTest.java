package gps.map.navigator.view.viewmodel.callback;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.ui.callback.FindMyPlaceCallback;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class FindPlaceCallbackTest {
    private PlaceProxyListener listener;

    @Before
    public void setUp() throws Exception {
        listener = mock(PlaceProxyListener.class);
    }

    private FindPlaceCallback createCallback() {
        FindPlaceCallback callback = new FindPlaceCallback();

        setInternalState(callback, listener, listener);

        return callback;
    }

    @Test
    public void make_onPlaceLocated_verify() {
        FindPlaceCallback callback = createCallback();

        IMapPlace mapPlace = mock(IMapPlace.class);
        callback.onPlaceLocated(mapPlace);

        verify(listener).onPlaceLocated(eq(mapPlace));
    }

    @Test
    public void make_onPlacesLocated_verify() {
        FindPlaceCallback callback = createCallback();

        List<IMapPlace> places = new ArrayList<>();
        callback.onPlacesLocated(places);

        verify(listener).onPlacesLocated(eq(places));
    }
}