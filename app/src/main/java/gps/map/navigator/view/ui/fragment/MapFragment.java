package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomappbar.BottomAppBar;

import javax.inject.Inject;

import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.DecorController;

public class MapFragment extends AbstractNaviFragment {
    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;

    @Override
    public String getFragmentTag() {
        return MapFragment.class.getName();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.showMap();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareUiForMainSceen();
    }

    private void prepareUiForMainSceen() {
        decorController.setBottomBarVisibility(true);
        decorController.setShowMeOnMapFabVisibility(true);
        decorController.setFabVisibility(true);
        decorController.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
    }
}
