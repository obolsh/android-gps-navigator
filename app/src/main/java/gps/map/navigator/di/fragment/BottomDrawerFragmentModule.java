package gps.map.navigator.di.fragment;

import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gps.map.navigator.common.Constants;
import gps.map.navigator.view.ui.fragment.BottomMenuFragment;
import gps.map.navigator.view.ui.fragment.listener.NavigationViewListener;
import gps.map.navigator.view.ui.fragment.listener.NightModeListener;
import gps.map.navigator.view.ui.fragment.listener.SatelliteModeListener;
import gps.map.navigator.view.ui.fragment.listener.TrafficModeListener;

@Module
public class BottomDrawerFragmentModule {

    @Provides
    Fragment provideFragment(BottomMenuFragment fragment) {
        return fragment;
    }

    @FragmentScope
    @Provides
    NavigationView.OnNavigationItemSelectedListener provideNavigationListener(NavigationViewListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    @Named(Constants.NightModeListener)
    CompoundButton.OnCheckedChangeListener provideNightModeListener(NightModeListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    @Named(Constants.TrafficModeListener)
    CompoundButton.OnCheckedChangeListener provideTrafficModeListener(TrafficModeListener listener) {
        return listener;
    }

    @FragmentScope
    @Provides
    @Named(Constants.SatelliteModeListener)
    CompoundButton.OnCheckedChangeListener provideSatelliteModeListener(SatelliteModeListener listener) {
        return listener;
    }
}
