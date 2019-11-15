package gps.map.navigator.view.ui.fragment;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.DecorController;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

public class MapFragmentTest {

    private DecorController decorController;
    private Presenter presenter;

    @Before
    public void setUp() throws Exception {
        decorController = mock(DecorController.class);
        presenter = mock(Presenter.class);
    }

    private MapFragment createFragment() {
        MapFragment fragment = new MapFragment();
        setInnerFields(fragment);
        return fragment;
    }

    private void setInnerFields(Fragment fragment) {
        setInternalState(fragment, "presenter", presenter);
        setInternalState(fragment, "decorController", decorController);
    }

    @Test
    public void make_onStart_verify() {
        MapFragment fragment = createFragment();

        fragment.onStart();

        verify(presenter).setLastOrigin(nullable(IMapPlace.class));
        verify(presenter).setLastDestination(nullable(IMapPlace.class));
    }

    @Test
    public void make_onActivityCreated_verify() {
        MapFragment fragment = createFragment();

        fragment.onActivityCreated(null);

        verify(presenter).showMap();
        verify(decorController).setBottomBarVisibility(eq(true));
        verify(decorController).setFabVisibility(eq(true));
        verify(decorController).setShowMeOnMapFabVisibility(eq(true));
        verify(decorController).setFabAlignmentMode(eq(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER));
    }
}