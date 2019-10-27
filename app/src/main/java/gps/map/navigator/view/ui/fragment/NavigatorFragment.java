package gps.map.navigator.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import demo.fragment.FragmentNavigation;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.controller.IFragment;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.NavigateCallback;

public class NavigatorFragment extends FragmentNavigation implements IFragment<Fragment> {

    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;
    @Inject
    Cache cache;

    @Override
    public Fragment getInstance() {
        return this;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.navigate(cache.getLastRoute(), new NavigateCallback());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndFab();
    }

    private void hideBottomBarAndFab() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(false);
        decorController.setShowMeOnMapFabVisibility(false);
    }

    @Override
    public String getFragmentTag() {
        return NavigatorFragment.class.getName();
    }
}
