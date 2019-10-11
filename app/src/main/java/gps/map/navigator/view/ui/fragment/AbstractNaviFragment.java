package gps.map.navigator.view.ui.fragment;

import androidx.fragment.app.Fragment;

import gps.map.navigator.view.ui.fragment.controller.IFragment;

public abstract class AbstractNaviFragment extends Fragment implements IFragment<Fragment> {

    @Override
    public String getFragmentTag() {
        return getTag();
    }

    @Override
    public Fragment getInstance() {
        return this;
    }
}
