package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.NavigatorFragment;

@Module
class NavigateFragmentModule {

    @Provides
    Fragment provideFragment(NavigatorFragment fragment) {
        return fragment;
    }
}
