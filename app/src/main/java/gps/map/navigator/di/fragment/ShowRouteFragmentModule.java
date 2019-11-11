package gps.map.navigator.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.fragment.ShowRouteFragment;
import gps.map.navigator.view.ui.fragment.listener.ISwipeRoute;

@Module(includes = {CallbackModule.class, SwipeModule.class})
public class ShowRouteFragmentModule {

    @Provides
    Fragment provideFragment(ShowRouteFragment fragment) {
        return fragment;
    }

    @FragmentScope
    @Provides
    ISwipeRoute provideSwipeRoute(ShowRouteFragment fragment) {
        return fragment;
    }
}
