package gps.map.navigator.view.ui.fragment.controller;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gps.map.navigator.view.ui.fragment.MapFragment;
import gps.map.navigator.view.ui.fragment.NavigatorFragment;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, NaviFragmentController.class, Fragment.class})
public class NaviFragmentControllerTest {

    private FragmentManager fragmentManager;
    private IFragment<Fragment> fragment;
    private FragmentTransaction transaction;
    private Fragment instance;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class);
        fragmentManager = mock(FragmentManager.class);
        fragment = mock(IFragment.class);
        transaction = mock(FragmentTransaction.class);
        instance = mock(MapFragment.class);

        when(fragmentManager.beginTransaction()).thenReturn(transaction);
        when(fragment.getInstance()).thenReturn(instance);
        when(fragment.getFragmentTag()).thenReturn(instance.getClass().getName());
        when(fragmentManager.findFragmentById(anyInt())).thenReturn(instance);
    }

    private void setReferences(NaviFragmentController controller) {
        setInternalState(controller, "fragmentManager", fragmentManager);
    }

    @Test
    public void make_openFragment_verify() {
        NaviFragmentController controller = new NaviFragmentController();
        setReferences(controller);
        String tag = instance.getClass().getName();

        controller.openFragment(fragment);

        verify(transaction).replace(anyInt(), eq(instance), eq(tag));
        verify(transaction).addToBackStack(eq(tag));
        verify(transaction).commit();
    }

    @Test
    public void make_thisFragmentIsActive_verify() {
        NaviFragmentController controller = new NaviFragmentController();
        setReferences(controller);

        boolean is_active = controller.thisFragmentIsActive(MapFragment.class);

        assertTrue(is_active);
    }

    @Test
    public void make_removeFromBackStack_verify() {
        NaviFragmentController controller = new NaviFragmentController();
        setReferences(controller);

        controller.removeFromBackStack(fragment);

        verify(transaction).remove(eq(instance));
        verify(transaction).commit();
        verify(fragmentManager).popBackStack();
    }
}