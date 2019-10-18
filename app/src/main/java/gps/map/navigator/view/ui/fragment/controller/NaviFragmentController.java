package gps.map.navigator.view.ui.fragment.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import javax.inject.Inject;
import javax.inject.Named;

import gps.map.navigator.common.Constants;

public class NaviFragmentController implements IFragmentController<Fragment> {

    @Inject
    FragmentManager fragmentManager;

    @Inject
    @Named(Constants.ContainerId)
    int container;

    private IFragment<Fragment> activeFragment;

    @Inject
    NaviFragmentController() {
    }

    @Override
    public void openFragment(IFragment<Fragment> fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(container, fragment.getInstance(), fragment.getFragmentTag());
        transaction.commit();
        activeFragment = fragment;
    }

    @Override
    public void backToLastFragment() {
        if (activeFragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(activeFragment.getInstance());
            transaction.commit();
        }
        activeFragment = null;
    }
}
