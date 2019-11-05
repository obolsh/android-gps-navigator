package gps.map.navigator.di.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.common.Constants;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.ui.fragment.ShowRouteFragment;
import gps.map.navigator.view.ui.fragment.listener.DestinationChangeListener;
import gps.map.navigator.view.ui.fragment.listener.DestinationClickListener;
import gps.map.navigator.view.ui.fragment.listener.ISwipeRoute;
import gps.map.navigator.view.ui.fragment.listener.OriginChangeListener;
import gps.map.navigator.view.ui.fragment.listener.OriginClickListener;
import gps.map.navigator.view.ui.fragment.listener.SwipePlacesListener;

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

//    @Provides
//    @Named(Constants.SwipePlacesListener)
//    View.OnClickListener provideSwipePlacesListener(SwipePlacesListener listener) {
//        return listener;
//    }
//
//    @Provides
//    @Named(Constants.OriginChangeListener)
//    PlaceProxyListener provideOriginChangeListener(OriginChangeListener listener) {
//        return listener;
//    }
//
//    @Provides
//    @Named(Constants.DestinationChangeListener)
//    PlaceProxyListener provideDestinationChangeListener(DestinationChangeListener listener) {
//        return listener;
//    }
//
//    @Provides
//    @Named(Constants.OriginClickListener)
//    View.OnClickListener provideOriginClickListener(OriginClickListener listener) {
//        return listener;
//    }
//
//    @Provides
//    @Named(Constants.DestinationClickListener)
//    View.OnClickListener provideDestinationClickListener(DestinationClickListener listener) {
//        return listener;
//    }
}
