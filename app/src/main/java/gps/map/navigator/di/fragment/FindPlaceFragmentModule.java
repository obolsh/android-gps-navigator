package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.FindPlaceFragment;

@Module
public class FindPlaceFragmentModule {

    @Provides
    Fragment provideFragment(FindPlaceFragment fragment) {
        return fragment;
    }
}
