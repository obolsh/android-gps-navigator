package gps.map.navigator.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.model.impl.data.MapSettingImpl;
import gps.map.navigator.model.impl.sdk.MapBoxSdkImpl;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.presenter.impl.MapTypeController;
import gps.map.navigator.presenter.impl.PresenterImpl;
import gps.map.navigator.presenter.interfaces.IMapTypeController;
import gps.map.navigator.presenter.interfaces.Presenter;

@Module (includes = {CacheModule.class})
public class CoreModule {

    @Singleton
    @Provides
    MapSdk provideMapSdk(MapBoxSdkImpl mapBoxSdk) {
        return mapBoxSdk;
    }

    @Singleton
    @Provides
    MapSetting provideMapSettings(MapSettingImpl mapSetting) {
        return mapSetting;
    }


    @Singleton
    @Provides
    Presenter providePresenter(PresenterImpl presenter) {
        return presenter;
    }

    @Singleton
    @Provides
    IMapTypeController provideIMapTypeController(MapTypeController controller) {
        return controller;
    }
}

