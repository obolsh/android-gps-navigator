package gps.map.navigator.view.ui.fragment.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import javax.inject.Inject;
import javax.inject.Named;

import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.view.ui.fragment.MapFragment;

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
    public void openFragment(@NonNull IFragment<Fragment> fragment) {
        String tag = fragment.getFragmentTag();
        if (tag != null && !tag.isEmpty()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(getEnterAnimation(tag), R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(container, fragment.getInstance(), tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
    }

    private int getEnterAnimation(String tag) {
        return tag.equals(MapFragment.class.getName()) ? 0 : R.anim.enter_from_right;
    }

    @Override
    public boolean thisFragmentIsActive(@NonNull Class cls) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null) {
            return fragment.getClass().getName().startsWith(cls.getName());
        }
        return false;
    }

    @Override
    public void removeFromBackStack(@NonNull IFragment<Fragment> fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment.getInstance());
        transaction.commit();
        fragmentManager.popBackStack();
    }

    @Override
    public String getActiveFragmentTag() {
        Fragment fragment = getVisibleFragment();
        return fragment != null ? fragment.getClass().getName() : "";
    }

    @Override
    public void reloadFragment(@NonNull IFragment<Fragment> fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(fragment.getInstance());
        transaction.attach(fragment.getInstance());
        transaction.commit();
    }

    @Nullable
    private Fragment getVisibleFragment() {
        return fragmentManager.findFragmentById(container);
    }
}
