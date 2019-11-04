package gps.map.navigator.di.fragment;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.viewmodel.callback.BuildRouteCallback;

@Module
public class CallbackModule {

    @Provides
    IPlaceHistoryListener provideBuildRouteCallback(BuildRouteCallback callback) {
        return callback;
    }
}
