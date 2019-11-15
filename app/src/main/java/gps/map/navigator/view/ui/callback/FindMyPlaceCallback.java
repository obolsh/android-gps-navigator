package gps.map.navigator.view.ui.callback;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import javax.inject.Inject;

import gps.map.navigator.common.Constants;
import gps.map.navigator.model.interfaces.Invalidator;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.callback.ShowMeOnMapCallback;

public class FindMyPlaceCallback implements View.OnClickListener, Invalidator {

    @Inject
    Presenter presenter;
    @Inject
    Activity activity;

    @Inject
    FindMyPlaceCallback() {
    }

    @Override
    public void onClick(View v) {
        if (hasLocatioPermission()) {
            if (isGpsActive()) {
                presenter.showMeOnMap(new ShowMeOnMapCallback());
            } else {
                requestLocationService();
            }

        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                Constants.REQUEST_ACCESS_FINE_LOCATION);
    }

    @Override
    public void invalidate() {
        presenter = null;
    }

    private boolean hasLocatioPermission() {
        return getPermissionState() == PackageManager.PERMISSION_GRANTED;
    }

    private int getPermissionState() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean isGpsActive() {
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        try {
            return lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            return false;
        }
    }

    private void requestLocationService() {
        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
}
