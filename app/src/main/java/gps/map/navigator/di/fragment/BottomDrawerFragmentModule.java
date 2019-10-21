package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.BottomMenuFragment;

@Module
public class BottomDrawerFragmentModule {

    @Provides
    Fragment provideFragment(BottomMenuFragment fragment) {
        return fragment;
    }
}
