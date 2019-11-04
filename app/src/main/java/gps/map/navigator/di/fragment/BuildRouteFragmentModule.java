package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

@Module(includes = {AdapterModule.class, CallbackModule.class})
public class BuildRouteFragmentModule {

    @Provides
    Fragment provideFragment(BuildRouteFragment fragment) {
        return fragment;
    }

    @Provides
    IPlacePickerCallback provideIPlacePickerCallback(BuildRouteFragment fragment) {
        return fragment;
    }

    @Provides
    ICachedPlaceCallback provideICachedPlaceCallback(BuildRouteFragment fragment) {
        return fragment;
    }
}
