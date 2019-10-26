package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.ShowRouteFragment;

@Module
class ShowRouteFragmentModule {

    @Provides
    Fragment provideFragment(ShowRouteFragment fragment) {
        return fragment;
    }
}
