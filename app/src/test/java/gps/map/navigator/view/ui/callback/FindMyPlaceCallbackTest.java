package gps.map.navigator.view.ui.callback;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.callback.ShowMeOnMapCallback;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.setInternalState;

public class FindMyPlaceCallbackTest {

    private Presenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = mock(Presenter.class);
    }

    private FindMyPlaceCallback initCallback() {
        FindMyPlaceCallback callback = new FindMyPlaceCallback();
        setInternalState(callback, "presenter", presenter);
        return callback;
    }

    @Test
    public void receive_click_verify() {
        FindMyPlaceCallback callback = initCallback();

        callback.onClick(null);

        verify(presenter).showMeOnMap(any(ShowMeOnMapCallback.class));

    }

    @Test
    public void call_invalidate_verify() {
        FindMyPlaceCallback callback = initCallback();

        callback.invalidate();

        Presenter presenter = getInternalState(callback, "presenter");

        assertNull(presenter);
    }
}