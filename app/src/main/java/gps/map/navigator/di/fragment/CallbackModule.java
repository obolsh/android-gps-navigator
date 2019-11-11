package gps.map.navigator.di.fragment;

import android.view.View;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.common.Constants;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.ui.fragment.listener.BackPressListener;
import gps.map.navigator.view.ui.fragment.listener.SwipePlacesListener;
import gps.map.navigator.view.viewmodel.callback.BuildRouteCallback;

@Module
public class CallbackModule {
    @FragmentScope
    @Provides
    IPlaceHistoryListener provideBuildRouteCallback(BuildRouteCallback callback) {
        return callback;
    }

    @FragmentScope
    @Provides
    @Named(Constants.BackPressListener)
    View.OnClickListener provideBackPressListener(BackPressListener listener) {
        return listener;
    }
}
