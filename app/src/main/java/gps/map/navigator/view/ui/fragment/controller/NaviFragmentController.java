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

    @Inject
    NaviFragmentController() {
    }

    @Override
    public void openFragment(IFragment<Fragment> fragment) {
        String tag = fragment.getFragmentTag();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(container, fragment.getInstance(), tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public boolean thisFragmentIsActive(Class cls) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null) {
            return fragment.getClass().getName().startsWith(cls.getName());
        }
        return false;
    }

    @Override
    public void removeFromBackStack(IFragment<Fragment> fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment.getInstance());
        transaction.commit();
        fragmentManager.popBackStack();
    }

    private Fragment getVisibleFragment() {
        return fragmentManager.findFragmentById(container);
    }
}
