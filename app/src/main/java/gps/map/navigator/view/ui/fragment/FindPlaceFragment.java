package gps.map.navigator.view.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.FindPlaceCallback;

public class FindPlaceFragment extends AbstractNaviFragment {

    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;
    private PlaceProxyListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndFab();
        presenter.findPlace(new FindPlaceCallback(listener));
    }

    private void hideBottomBarAndFab() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(false);
        decorController.setShowMeOnMapFabVisibility(false);
    }

    @Override
    public String getFragmentTag() {
        return FindPlaceFragment.class.getName();
    }

    public void setTask(PlaceProxyListener listener) {
        this.listener = listener;
    }
}
