package gps.map.navigator.di.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.common.Constants;
import gps.map.navigator.view.ui.fragment.ShowRouteFragment;
import gps.map.navigator.view.ui.fragment.listener.BackPressListener;

@Module (includes = CallbackModule.class)
public class ShowRouteFragmentModule {

    @Provides
    Fragment provideFragment(ShowRouteFragment fragment) {
        return fragment;
    }
}
