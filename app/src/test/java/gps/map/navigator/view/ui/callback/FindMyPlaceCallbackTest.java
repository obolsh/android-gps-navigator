package gps.map.navigator.view.ui.callback;

import android.app.Activity;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gps.map.navigator.common.utils.PermissionHelper;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.callback.ShowMeOnMapCallback;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FindMyPlaceCallback.class, ContextCompat.class, ActivityCompat.class, Intent.class})
public class FindMyPlaceCallbackTest {

    private Presenter presenter;
    private Activity activity;
    private PermissionHelper permissionHelper;

    @Before
    public void setUp() throws Exception {
        presenter = mock(Presenter.class);
        activity = mock(Activity.class);
        permissionHelper = mock(PermissionHelper.class);
        mockStatic(ContextCompat.class);
        mockStatic(ActivityCompat.class);
        whenNew(PermissionHelper.class).withAnyArguments().thenReturn(permissionHelper);
    }

    private FindMyPlaceCallback initCallback() {
        FindMyPlaceCallback callback = new FindMyPlaceCallback();
        setInternalState(callback, "presenter", presenter);
        setInternalState(callback, "activity", activity);
        return callback;
    }

    @Test
    public void receive_click_has_permission_has_location_verify() {
        FindMyPlaceCallback callback = initCallback();
        when(permissionHelper.hasLocationPermission()).thenReturn(true);
        when(permissionHelper.isGpsActive()).thenReturn(true);

        callback.onClick(null);

        verify(presenter).showMeOnMap(any(ShowMeOnMapCallback.class));

    }

    @Test
    public void receive_click_has_permission_missing_location_verify() {
        FindMyPlaceCallback callback = initCallback();
        when(permissionHelper.hasLocationPermission()).thenReturn(true);
        when(permissionHelper.isGpsActive()).thenReturn(false);

        callback.onClick(null);

        verify(permissionHelper).requestLocationService();

    }

    @Test
    public void receive_click_missing_permission_verify() {
        FindMyPlaceCallback callback = initCallback();
        when(permissionHelper.hasLocationPermission()).thenReturn(false);
        when(permissionHelper.isGpsActive()).thenReturn(false);

        callback.onClick(null);

        verify(presenter, times(0)).showMeOnMap(any(ShowMeOnMapCallback.class));
        verify(permissionHelper).requestLocationPermission();
    }

    @Test
    public void call_invalidate_verify() {
        FindMyPlaceCallback callback = initCallback();

        callback.invalidate();

        Presenter presenter = getInternalState(callback, "presenter");

        assertNull(presenter);
    }
}