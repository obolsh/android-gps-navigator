package gps.map.navigator.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.common.Constants;
import gps.map.navigator.common.cache.DatabaseStorage;
import gps.map.navigator.common.cache.Storage;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.common.debug.LoggerImpl;
import gps.map.navigator.model.DataCache;
import gps.map.navigator.model.interfaces.Cache;

@Module
public class CacheModule {

    @Provides
    @Singleton
    Logger provideLogger(LoggerImpl logger) {
        return logger;
    }

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

    @Singleton
    @Provides
    Storage provideStorage(DatabaseStorage storage) {
        return storage;
    }

    @Singleton
    @Provides
    Cache provideCache(DataCache cache) {
        return cache;
    }
}
