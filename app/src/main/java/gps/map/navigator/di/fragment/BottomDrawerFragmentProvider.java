package gps.map.navigator.di.fragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import gps.map.navigator.view.ui.fragment.BottomMenuFragment;

@Module
public abstract class BottomDrawerFragmentProvider {

    @ContributesAndroidInjector(modules = BottomDrawerFragmentModule.class)
    abstract BottomMenuFragment provideBottomDrawerFragmentFactory();
}
