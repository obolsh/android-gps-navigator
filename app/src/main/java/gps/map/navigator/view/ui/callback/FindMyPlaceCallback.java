package gps.map.navigator.view.ui.callback;

import android.app.Activity;
import android.view.View;

import javax.inject.Inject;

import gps.map.navigator.common.utils.PermissionHelper;
import gps.map.navigator.model.interfaces.Invalidator;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.callback.ShowMeOnMapCallback;

public class FindMyPlaceCallback implements View.OnClickListener, Invalidator {

    @Inject
    Presenter presenter;
    @Inject
    Activity activity;
    private PermissionHelper permissionHelper;

    @Inject
    FindMyPlaceCallback() {
    }

    @Override
    public void onClick(View v) {
        if (hasLocationPermission()) {
            if (isGpsActive()) {
                presenter.showMeOnMap(new ShowMeOnMapCallback());
            } else {
                requestLocationService();
            }

        } else {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        getPermissionHelper().requestLocationPermission();
    }

    @Override
    public void invalidate() {
        presenter = null;
    }

    private boolean hasLocationPermission() {
        return getPermissionHelper().hasLocationPermission();
    }

    private PermissionHelper getPermissionHelper() {
        if (permissionHelper == null) {
            permissionHelper = new PermissionHelper(activity);
        }
        return permissionHelper;
    }

    private boolean isGpsActive() {
        return getPermissionHelper().isGpsActive();
    }

    private void requestLocationService() {
        getPermissionHelper().requestLocationService();
    }
}
