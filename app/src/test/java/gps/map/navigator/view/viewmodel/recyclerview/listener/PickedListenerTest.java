package gps.map.navigator.view.viewmodel.recyclerview.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PickedListenerTest {
    private IMapPlace mapPlace;
    private IPlacePickerCallback placePickerCallback;

    @Before
    public void setUp() throws Exception {
        mapPlace = mock(IMapPlace.class);
        placePickerCallback = mock(IPlacePickerCallback.class);
    }

    @Test
    public void receive_onClick_verify() {
        PickedListener listener = new PickedListener(mapPlace, placePickerCallback);

        listener.onClick(null);

        verify(placePickerCallback).setNewPickedPlace(eq(mapPlace));
    }
}