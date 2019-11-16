package gps.map.navigator.view.ui.callback;

import androidx.fragment.app.Fragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gps.map.navigator.common.utils.PermissionHelper;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;
import gps.map.navigator.view.ui.fragment.MapFragment;
import gps.map.navigator.view.ui.fragment.NavigatorFragment;
import gps.map.navigator.view.ui.fragment.ShowPlaceFragment;
import gps.map.navigator.view.ui.fragment.ShowRouteFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NextCallbackListener.class})
public class NextCallbackListenerTest {
    private IFragmentController<Fragment> fragmentController;
    private BuildRouteFragment buildRouteFragment;
    private NavigatorFragment navigatorFragment;
    private PermissionHelper permissionHelper;

    @Before
    public void setUp() throws Exception {
        fragmentController = mock(IFragmentController.class);
        buildRouteFragment = mock(BuildRouteFragment.class);
        navigatorFragment = mock(NavigatorFragment.class);
        permissionHelper = mock(PermissionHelper.class);

        whenNew(BuildRouteFragment.class).withAnyArguments().thenReturn(buildRouteFragment);
        whenNew(NavigatorFragment.class).withAnyArguments().thenReturn(navigatorFragment);
        whenNew(PermissionHelper.class).withAnyArguments().thenReturn(permissionHelper);
    }

    @After
    public void tearDown() throws Exception {
        when(fragmentController.thisFragmentIsActive(any(Class.class))).thenReturn(false);
    }

    @Test
    public void receive_click_missing_controller_verify() {
        NextCallbackListener listener = new NextCallbackListener();

        listener.onClick(null);

        verify(fragmentController, times(0)).thisFragmentIsActive(any(Class.class));
    }

    private NextCallbackListener initListener() {
        NextCallbackListener listener = new NextCallbackListener();
        setInternalState(listener, "fragmentController", fragmentController);
        return listener;
    }

    @Test
    public void receive_click_map_active_verify() {
        NextCallbackListener listener = initListener();
        when(fragmentController.thisFragmentIsActive(eq(MapFragment.class))).thenReturn(true);

        listener.onClick(null);

        verify(fragmentController).openFragment(eq(buildRouteFragment));
    }

    @Test
    public void receive_click_show_place_active_verify() {
        NextCallbackListener listener = initListener();
        when(fragmentController.thisFragmentIsActive(eq(ShowPlaceFragment.class))).thenReturn(true);

        listener.onClick(null);

        verify(fragmentController).openFragment(eq(buildRouteFragment));
    }

    @Test
    public void receive_click_show_route_no_permission_active_verify() {
        NextCallbackListener listener = initListener();
        when(fragmentController.thisFragmentIsActive(eq(ShowRouteFragment.class))).thenReturn(true);
        when(permissionHelper.hasLocationPermission()).thenReturn(false);
        when(permissionHelper.isGpsActive()).thenReturn(false);

        listener.onClick(null);

        verify(permissionHelper).requestLocationPermission();
    }

    @Test
    public void receive_click_show_route_no_location_active_verify() {
        NextCallbackListener listener = initListener();
        when(fragmentController.thisFragmentIsActive(eq(ShowRouteFragment.class))).thenReturn(true);
        when(permissionHelper.hasLocationPermission()).thenReturn(true);
        when(permissionHelper.isGpsActive()).thenReturn(false);

        listener.onClick(null);

        verify(permissionHelper).requestLocationService();
    }

    @Test
    public void receive_click_show_route_has_location_has_permission_active_verify() {
        NextCallbackListener listener = initListener();
        when(fragmentController.thisFragmentIsActive(eq(ShowRouteFragment.class))).thenReturn(true);
        when(permissionHelper.hasLocationPermission()).thenReturn(true);
        when(permissionHelper.isGpsActive()).thenReturn(true);

        listener.onClick(null);

        verify(fragmentController).openFragment(eq(navigatorFragment));
    }
}