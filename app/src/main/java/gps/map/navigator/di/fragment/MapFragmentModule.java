package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.MapFragment;

@Module
public class MapFragmentModule {

    @Provides
    Fragment provideFragment(MapFragment fragment) {
        return fragment;
    }
}
