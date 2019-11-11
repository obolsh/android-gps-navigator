package gps.map.navigator.view.ui.fragment.listener;

import androidx.fragment.app.Fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.ui.fragment.FindPlaceFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OriginClickListener.class})
public class OriginClickListenerTest {

    private IFragmentController<Fragment> fragmentController;
    private PlaceProxyListener listener;
    private FindPlaceFragment findPlaceFragment;

    @Before
    public void setUp() throws Exception {
        fragmentController = mock(IFragmentController.class);
        listener = mock(PlaceProxyListener.class);
        findPlaceFragment = mock(FindPlaceFragment.class);

        whenNew(FindPlaceFragment.class).withAnyArguments().thenReturn(findPlaceFragment);
    }

    @Test
    public void receive_onClick_verify() {
        OriginClickListener clickListener = new OriginClickListener();
        setInternalState(clickListener, "fragmentController", fragmentController);
        setInternalState(clickListener, "listener", listener);

        clickListener.onClick(null);

        verify(findPlaceFragment).setTask(eq(listener));
        verify(fragmentController).openFragment(eq(findPlaceFragment));
    }
}