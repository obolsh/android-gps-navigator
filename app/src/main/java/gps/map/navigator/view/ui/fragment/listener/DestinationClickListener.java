package gps.map.navigator.view.ui.fragment.listener;

import android.view.View;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;
import javax.inject.Named;

import gps.map.navigator.common.Constants;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.ui.fragment.FindPlaceFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;

public class DestinationClickListener implements View.OnClickListener {
    @Inject
    IFragmentController<Fragment> fragmentController;
    @Inject
    @Named(Constants.DestinationChangeListener)
    PlaceProxyListener listener;

    @Inject
    DestinationClickListener() {
    }

    @Override
    public void onClick(View v) {
        FindPlaceFragment fragment = new FindPlaceFragment();
        fragment.setTask(listener);
        fragmentController.openFragment(fragment);
    }
}
