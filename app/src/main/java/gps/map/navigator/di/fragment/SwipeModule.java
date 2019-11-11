package gps.map.navigator.di.fragment;

import android.view.View;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.common.Constants;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.ui.fragment.listener.DestinationChangeListener;
import gps.map.navigator.view.ui.fragment.listener.DestinationClickListener;
import gps.map.navigator.view.ui.fragment.listener.OriginChangeListener;
import gps.map.navigator.view.ui.fragment.listener.OriginClickListener;
import gps.map.navigator.view.ui.fragment.listener.SwipePlacesListener;

@Module
public class SwipeModule {

    @FragmentScope
    @Provides
    @Named(Constants.SwipePlacesListener)
    View.OnClickListener provideSwipePlacesListener(SwipePlacesListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    @Named(Constants.OriginChangeListener)
    PlaceProxyListener provideOriginChangeListener(OriginChangeListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    @Named(Constants.DestinationChangeListener)
    PlaceProxyListener provideDestinationChangeListener(DestinationChangeListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    @Named(Constants.OriginClickListener)
    View.OnClickListener provideOriginClickListener(OriginClickListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    @Named(Constants.DestinationClickListener)
    View.OnClickListener provideDestinationClickListener(DestinationClickListener listener) {
        return listener;
    }
}
