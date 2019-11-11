package gps.map.navigator.view.ui.fragment;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.listener.ShowSinglePlaceListener;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.FindAndShowCallback;
import gps.map.navigator.view.viewmodel.callback.NavigateCallback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;

public class ShowPlaceFragmentTest {

    private DecorController decorController;
    private Presenter presenter;
    private IMapPlace place;

    @Before
    public void setUp() throws Exception {
        decorController = mock(DecorController.class);
        presenter = mock(Presenter.class);
        place = mock(IMapPlace.class);

        when(presenter.getLastPlace()).thenReturn(place);
    }

    private ShowPlaceFragment createFragment() {
        ShowPlaceFragment fragment = new ShowPlaceFragment();
        setInnerFields(fragment);
        return fragment;
    }

    private void setInnerFields(Fragment fragment) {
        setInternalState(fragment, "presenter", presenter);
        setInternalState(fragment, "decorController", decorController);
    }

    @Test
    public void make_onStart_verify() {
        ShowPlaceFragment fragment = createFragment();

        fragment.onStart();

        verify(presenter).setLastOrigin(eq(place));
        verify(presenter).setLastDestination(nullable(IMapPlace.class));
        verify(presenter).showPlace(eq(place), any(FindAndShowCallback.class));
    }

    @Test
    public void make_onActivityCreated_verify() {
        ShowPlaceFragment fragment = createFragment();

        fragment.onActivityCreated(null);

        verify(decorController).setBottomBarVisibility(eq(false));
        verify(decorController).setFabVisibility(eq(true));
        verify(decorController).setShowMeOnMapFabVisibility(eq(true));
        verify(decorController).setFabAlignmentMode(eq(BottomAppBar.FAB_ALIGNMENT_MODE_END));
    }
}