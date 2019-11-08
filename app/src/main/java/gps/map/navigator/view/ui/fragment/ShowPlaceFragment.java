package gps.map.navigator.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import demo.fragment.FragmentMap;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.controller.IFragment;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.FindAndShowCallback;

public class ShowPlaceFragment extends FragmentMap implements IFragment<Fragment> {

    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;
    @Inject
    Cache cache;

    @Override
    public void onStart() {
        super.onStart();
        IMapPlace lastPlace = cache.getLastPlace();
        cache.setLastOrigin(lastPlace);
        cache.setLastDestination(null);
        if (lastPlace != null) {
            presenter.showPlace(lastPlace, new FindAndShowCallback());
        }
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
        return ShowPlaceFragment.class.getName();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndFab();
    }

    private void hideBottomBarAndFab() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(true);
        decorController.setShowMeOnMapFabVisibility(true);
        decorController.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
    }
}
