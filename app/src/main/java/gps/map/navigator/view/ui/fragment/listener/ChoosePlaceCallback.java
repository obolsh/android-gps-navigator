package gps.map.navigator.view.ui.fragment.listener;

import android.view.View;

import androidx.fragment.app.Fragment;

import gps.map.navigator.view.ui.fragment.FindPlaceFragment;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;

public class ChoosePlaceCallback implements View.OnClickListener {
    private IFragmentController<Fragment> fragmentController;

    public ChoosePlaceCallback(IFragmentController<Fragment> fragmentController) {
        this.fragmentController = fragmentController;
    }

    @Override
    public void onClick(View v) {
        if (fragmentController != null) {
            fragmentController.openFragment(new FindPlaceFragment());
        }
        fragmentController = null;
    }
}
