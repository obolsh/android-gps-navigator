package gps.map.navigator.di.fragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import gps.map.navigator.view.ui.fragment.BottomDrawerFragment;

@Module
public abstract class BottomDrawerFragmentProvider {

    @ContributesAndroidInjector(modules = BottomDrawerFragmentModule.class)
    abstract BottomDrawerFragment provideBottomDrawerFragmentFactory();
}
