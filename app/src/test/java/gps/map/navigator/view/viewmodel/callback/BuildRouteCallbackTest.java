package gps.map.navigator.view.viewmodel.callback;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class BuildRouteCallbackTest {
    private ICachedPlaceCallback fragment;

    @Before
    public void setUp() throws Exception {
        fragment = mock(ICachedPlaceCallback.class);
    }

    private BuildRouteCallback createCallback() {
        BuildRouteCallback callback = new BuildRouteCallback();

        setInternalState(callback, "fragment", fragment);

        return callback;
    }

    @Test
    public void make_onHistoryPlacesFound_verify() {
        BuildRouteCallback callback = createCallback();

        List<IMapPlace> places = new ArrayList<>();
        callback.onHistoryPlacesFound(places);

        verify(fragment).setHistoryPlaces(eq(places));
    }
}