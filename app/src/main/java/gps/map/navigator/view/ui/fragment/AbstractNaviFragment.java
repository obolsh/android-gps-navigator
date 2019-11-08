package gps.map.navigator.view.ui.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dagger.android.support.AndroidSupportInjection;
import gps.map.navigator.view.ui.fragment.controller.IFragment;

public abstract class AbstractNaviFragment extends Fragment implements IFragment<Fragment> {

    @NonNull
    @Override
    public Fragment getInstance() {
        return this;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}
