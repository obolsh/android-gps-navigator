package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;
import gps.map.navigator.view.ui.fragment.listener.ISwipeRoute;

@Module(includes = {AdapterModule.class, CallbackModule.class, SwipeModule.class})
public class BuildRouteFragmentModule {

    @Provides
    Fragment provideFragment(BuildRouteFragment fragment) {
        return fragment;
    }

    @FragmentScope
    @Provides
    IPlacePickerCallback provideIPlacePickerCallback(BuildRouteFragment fragment) {
        return fragment;
    }

    @FragmentScope
    @Provides
    ICachedPlaceCallback provideICachedPlaceCallback(BuildRouteFragment fragment) {
        return fragment;
    }

    @FragmentScope
    @Provides
    ISwipeRoute provideSwipeRoute(BuildRouteFragment fragment) {
        return fragment;
    }
}
