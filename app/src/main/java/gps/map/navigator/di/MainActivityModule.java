package gps.map.navigator.di;

import android.app.Activity;
import android.view.View;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.view.ui.MainActivity;
import gps.map.navigator.view.ui.callback.FindMyPlaceCallback;
import gps.map.navigator.view.ui.callback.NextCallbackListener;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.ui.fragment.controller.NaviFragmentController;

@Module
class MainActivityModule {

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

    @Provides
    @Named(Constants.FindMyPlaceCallback)
    View.OnClickListener provideFindMyPlaceCallback(FindMyPlaceCallback callback) {
        return callback;
    }

    @Provides
    @Named(Constants.NextCallbackListener)
    View.OnClickListener provideNextCallbackListener(NextCallbackListener callback) {
        return callback;
    }
}
