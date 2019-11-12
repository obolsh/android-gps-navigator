package gps.map.navigator.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.controller.IFragment;
import gps.map.navigator.view.viewmodel.DecorController;

public class MapFragment extends FragmentMap implements IFragment<Fragment> {
    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;

    @Inject
    public MapFragment() {
    }

    @NonNull
    @Override
    public Fragment getInstance() {
        return this;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return MapFragment.class.getName();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.showMap();
        presenter.setLastOrigin(null);
        presenter.setLastDestination(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareUiElements();
    }

    private void prepareUiElements() {
        decorController.setBottomBarVisibility(true);
        decorController.setShowMeOnMapFabVisibility(true);
        decorController.setFabVisibility(true);
        decorController.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
    }
}
