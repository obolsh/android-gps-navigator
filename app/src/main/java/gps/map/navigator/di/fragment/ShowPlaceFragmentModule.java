package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.ShowPlaceFragment;

@Module
class ShowPlaceFragmentModule {

    @Provides
    Fragment provideFragment(ShowPlaceFragment fragment) {
        return fragment;
    }
}
