package gps.map.navigator.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.common.Constants;

@Module(includes = {CoreModule.class})
public class AppModule {

    @Provides
    @Named(Constants.ApplicationContext)
    Context provideContext(Application application) {
        return application;
    }
}
