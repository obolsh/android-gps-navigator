package gps.map.navigator.view.ui.fragment.listener;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class BackPressListenerTest {
    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activity = mock(Activity.class);
    }

    @Test
    public void receive_onClick_verify() {
        BackPressListener listener = new BackPressListener();
        setInternalState(listener, "activity", activity);

        listener.onClick(null);

        verify(activity).onBackPressed();
    }
}