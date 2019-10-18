package gps.map.navigator.view.ui.fragment.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.setInternalState;

public class NaviFragmentControllerTest {

    private FragmentManager fragmentManager;
    private IFragment<Fragment> fragment;
    private FragmentTransaction transaction;
    private Fragment instance;

    @Before
    public void setUp() throws Exception {
        fragmentManager = mock(FragmentManager.class);
        fragment = mock(IFragment.class);
        transaction = mock(FragmentTransaction.class);
        instance = mock(Fragment.class);

        when(fragmentManager.beginTransaction()).thenReturn(transaction);
        when(fragment.getInstance()).thenReturn(instance);
        when(fragment.getFragmentTag()).thenReturn("foo");
    }

    private void setReferences(NaviFragmentController controller) {
        setInternalState(controller, "fragmentManager", fragmentManager);
    }

    @Test
    public void make_openFragment_verify() {
        NaviFragmentController controller = new NaviFragmentController();
        setReferences(controller);

        controller.openFragment(fragment);

        verify(transaction).add(anyInt(), eq(instance), eq("foo"));
        verify(transaction).commit();

        assertNotNull(getInternalState(controller, "activeFragment"));
    }

    @Test
    public void make_backToLastFragment_verify() {
        NaviFragmentController controller = new NaviFragmentController();
        setReferences(controller);
        setInternalState(controller, "activeFragment", fragment);

        controller.backToLastFragment();

        verify(transaction).remove(eq(instance));
        verify(transaction).commit();

        assertNull(getInternalState(controller, "activeFragment"));
    }
}