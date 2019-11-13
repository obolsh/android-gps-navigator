package gps.map.navigator.view.ui.callback;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
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
            presenter.showMeOnMap(new ShowMeOnMapCallback());
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
}
