package gps.map.navigator.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.common.Constants;
import gps.map.navigator.common.cache.DatabaseStorage;
import gps.map.navigator.common.cache.Storage;
import gps.map.navigator.model.impl.common.DataCache;
import gps.map.navigator.model.impl.data.MapSettingImpl;
import gps.map.navigator.model.impl.sdk.MapBoxSdkImpl;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.model.interfaces.MapSetting;
import gps.map.navigator.presenter.impl.PresenterImpl;
import gps.map.navigator.presenter.interfaces.Presenter;

@Module
public class CoreModule {

    @Provides
    @Named(Constants.DatabaseInfo)
    String provideDatabaseName() {
        return "database_storage.db";
    }

    @Provides
    @Named(Constants.DatabaseInfo)
    Integer provideDatabaseVersion() {
        return 1;
    }

    @Provides
    Storage provideStorage(DatabaseStorage storage) {
        return storage;
    }

    @Provides
    MapSdk provideMapSdk(MapBoxSdkImpl mapBoxSdk) {
        return mapBoxSdk;
    }

    @Provides
    MapSetting provideMapSettings(MapSettingImpl mapSetting) {
        return mapSetting;
    }

    @Provides
    Cache provideCache(DataCache cache) {
        return cache;
    }

    @Provides
    Presenter providePresenter(PresenterImpl presenter) {
        return presenter;
    }
}
