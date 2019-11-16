package gps.map.navigator.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PermissionHelper.class, Intent.class, ContextCompat.class, ActivityCompat.class})
public class PermissionHelperTest {

    private Activity activity;
    private LocationManager locationManager;
    private Intent intent;

    @Before
    public void setUp() throws Exception {
        mockStatic(ContextCompat.class);
        mockStatic(ActivityCompat.class);
        activity = mock(Activity.class);
        locationManager = mock(LocationManager.class);
        intent = mock(Intent.class);

        whenNew(Intent.class).withAnyArguments().thenReturn(intent);
        when(activity.getSystemService(eq(Context.LOCATION_SERVICE))).thenReturn(locationManager);
    }

    @Test
    public void requested_hasLocationPermission_granted_verify() {
        PermissionHelper helper = new PermissionHelper(activity);
        when(ContextCompat.checkSelfPermission(eq(activity), eq(Manifest.permission.ACCESS_FINE_LOCATION))).thenReturn(PackageManager.PERMISSION_GRANTED);

        boolean granted = helper.hasLocationPermission();

        assertTrue(granted);
    }

    @Test
    public void requested_hasLocationPermission_denied_verify() {
        PermissionHelper helper = new PermissionHelper(activity);
        when(ContextCompat.checkSelfPermission(eq(activity), eq(Manifest.permission.ACCESS_FINE_LOCATION))).thenReturn(PackageManager.PERMISSION_DENIED);

        boolean granted = helper.hasLocationPermission();

        assertFalse(granted);
    }

    @Test
    public void requested_isGpsActive_granted_verify() {
        PermissionHelper helper = new PermissionHelper(activity);
        when(locationManager.isProviderEnabled(eq(LocationManager.GPS_PROVIDER))).thenReturn(true);

        boolean granted = helper.isGpsActive();

        assertTrue(granted);
    }

    @Test
    public void requested_isGpsActive_denied_verify() {
        PermissionHelper helper = new PermissionHelper(activity);
        when(locationManager.isProviderEnabled(eq(LocationManager.GPS_PROVIDER))).thenReturn(false);

        boolean granted = helper.isGpsActive();

        assertFalse(granted);
    }

    @Test
    public void requested_requestLocationService() {
        PermissionHelper helper = new PermissionHelper(activity);

        helper.requestLocationService();

        verify(activity).startActivity(eq(intent));
    }

    @Test
    public void requested_requestLocationPermission() {
        PermissionHelper helper = new PermissionHelper(activity);

        helper.requestLocationPermission();
    }
}