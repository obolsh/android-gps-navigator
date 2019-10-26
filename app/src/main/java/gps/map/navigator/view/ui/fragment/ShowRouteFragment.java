package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomappbar.BottomAppBar;

import javax.inject.Inject;

import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.ShowRouteCallback;

public class ShowRouteFragment extends AbstractNaviFragment {

    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;
    @Inject
    Cache cache;

    @Override
    public void onStart() {
        super.onStart();
        presenter.showRoute(cache.getLastRoute(), new ShowRouteCallback());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_route, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndMoveFabToRight();
    }

    private void hideBottomBarAndMoveFabToRight() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(true);
        decorController.setShowMeOnMapFabVisibility(true);
        decorController.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
    }

    @Override
    public String getFragmentTag() {
        return ShowRouteFragment.class.getName();
    }
}
