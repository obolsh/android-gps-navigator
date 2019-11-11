package gps.map.navigator.view.ui.fragment;

import androidx.fragment.app.Fragment;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.NavigateCallback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

public class NavigatorFragmentTest {

    private DecorController decorController;
    private Presenter presenter;
    private IRoute route;

    @Before
    public void setUp() throws Exception {
        decorController = mock(DecorController.class);
        presenter = mock(Presenter.class);
        route = mock(IRoute.class);

        when(presenter.getLastRoute()).thenReturn(route);
    }

    private NavigatorFragment createFragment() {
        NavigatorFragment fragment = new NavigatorFragment();
        setInnerFields(fragment);
        return fragment;
    }

    private void setInnerFields(Fragment fragment) {
        setInternalState(fragment, "presenter", presenter);
        setInternalState(fragment, "decorController", decorController);
    }

    @Test
    public void make_onStart_verify() {
        NavigatorFragment fragment = createFragment();

        fragment.onStart();

        verify(presenter).navigate(eq(route), any(NavigateCallback.class));
    }

    @Test
    public void make_onActivityCreated_verify() {
        NavigatorFragment fragment = createFragment();

        fragment.onActivityCreated(null);

        verify(decorController).setBottomBarVisibility(eq(false));
        verify(decorController).setFabVisibility(eq(false));
        verify(decorController).setShowMeOnMapFabVisibility(eq(false));
    }
}