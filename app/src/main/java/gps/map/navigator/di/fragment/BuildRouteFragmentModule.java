package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;

@Module
class BuildRouteFragmentModule {

    @Provides
    Fragment provideFragment(BuildRouteFragment fragment) {
        return fragment;
    }
}
