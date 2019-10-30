package gps.map.navigator.view.ui.fragment.listener;

import android.view.View;

import androidx.fragment.app.Fragment;

import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.ui.fragment.FindPlaceFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;

public class ChoosePlaceCallback implements View.OnClickListener {
    private IFragmentController<Fragment> fragmentController;
    private PlaceProxyListener listener;

    public ChoosePlaceCallback(IFragmentController<Fragment> fragmentController, PlaceProxyListener listener) {
        this.fragmentController = fragmentController;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (fragmentController != null) {
            FindPlaceFragment fragment = new FindPlaceFragment();
            fragment.setTask(listener);
            fragmentController.openFragment(fragment);
        }
    }
}
