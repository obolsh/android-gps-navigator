package gps.map.navigator.di;

import android.app.Activity;


import dagger.Module;
import dagger.Provides;
import gps.map.navigator.view.ui.MainActivity;

@Module
public class MainActivityModule {

    @Provides
    Activity provideActivity(MainActivity mainActivity) {
        return mainActivity;
    }
}
