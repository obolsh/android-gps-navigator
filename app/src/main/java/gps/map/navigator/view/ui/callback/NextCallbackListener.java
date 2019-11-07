package gps.map.navigator.view.ui.callback;

import android.view.View;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.Invalidator;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;
import gps.map.navigator.view.ui.fragment.MapFragment;
import gps.map.navigator.view.ui.fragment.NavigatorFragment;
import gps.map.navigator.view.ui.fragment.ShowPlaceFragment;
import gps.map.navigator.view.ui.fragment.ShowRouteFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;

public class NextCallbackListener implements View.OnClickListener, Invalidator {

    @Inject
    IFragmentController<Fragment> fragmentController;

    @Inject
    NextCallbackListener() {
    }

    @Override
    public void onClick(View v) {
        if (hasController()) {
            moveToNextFragment();
        }
    }

    private boolean hasController() {
        return fragmentController != null;
    }

    private void moveToNextFragment() {
        if (mapFragmentIsActive()) {
            openBuildRouteFragment();
        } else if (showPlaceFragmentIsActive()) {
            openBuildRouteFragment();
        } else if (showRouteFragmentIsActive()) {
            openNavigateFragment();
        }
    }

    private boolean mapFragmentIsActive() {
        return fragmentController.thisFragmentIsActive(MapFragment.class);
    }

    private void openBuildRouteFragment() {
        fragmentController.openFragment(new BuildRouteFragment());
    }

    private boolean showPlaceFragmentIsActive() {
        return fragmentController.thisFragmentIsActive(ShowPlaceFragment.class);
    }

    private boolean showRouteFragmentIsActive() {
        return fragmentController.thisFragmentIsActive(ShowRouteFragment.class);
    }

    private void openNavigateFragment() {
        fragmentController.openFragment(new NavigatorFragment());
    }

    @Override
    public void invalidate() {
        fragmentController = null;
    }
}
