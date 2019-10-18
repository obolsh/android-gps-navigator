package gps.map.navigator.di;

import android.app.Activity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.view.ui.MainActivity;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.ui.fragment.controller.NaviFragmentController;

@Module
public class MainActivityModule {

    @Provides
    Activity provideActivity(MainActivity mainActivity) {
        return mainActivity;
    }

    @Provides
    FragmentManager provideFragmentManager(MainActivity mainActivity) {
        return mainActivity.getSupportFragmentManager();
    }

    @Provides
    IFragmentController<Fragment> provideIFragmentController(NaviFragmentController controller) {
        return controller;
    }


    @Provides
    @Named(Constants.ContainerId)
    int provideContainerId() {
        return R.id.container;
    }
}
