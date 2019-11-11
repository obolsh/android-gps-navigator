package gps.map.navigator.view.ui.fragment.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.presenter.interfaces.Presenter;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class TrafficModeListenerTest {

    private Presenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = mock(Presenter.class);
    }

    @Test
    public void receive_onCheckedChanged_verify() {
        TrafficModeListener listener = new TrafficModeListener();
        setInternalState(listener, "presenter", presenter);

        listener.onCheckedChanged(null, true);

        verify(presenter).enableTraffic(eq(true));
    }
}