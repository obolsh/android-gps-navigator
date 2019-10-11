package gps.map.navigator.view.ui.fragment.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import gps.map.navigator.R;

public class NaviFragmentController implements IFragmentController<Fragment> {

    private FragmentManager fragmentManager;
    private IFragment<Fragment> activeFragment;

    public NaviFragmentController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void openFragment(IFragment<Fragment> fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, fragment.getInstance(), fragment.getFragmentTag());
        transaction.commit();
        activeFragment = fragment;
    }

    @Override
    public void backToLastFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(activeFragment.getInstance());
        transaction.commit();
    }
}
