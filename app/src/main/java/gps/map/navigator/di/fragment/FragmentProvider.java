package gps.map.navigator.di.fragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import gps.map.navigator.di.DecorModule;
import gps.map.navigator.view.ui.fragment.BottomMenuFragment;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;
import gps.map.navigator.view.ui.fragment.FindPlaceFragment;
import gps.map.navigator.view.ui.fragment.MapFragment;
import gps.map.navigator.view.ui.fragment.NavigatorFragment;
import gps.map.navigator.view.ui.fragment.ShowPlaceFragment;
import gps.map.navigator.view.ui.fragment.ShowRouteFragment;

@Module(includes = {DecorModule.class})
public abstract class FragmentProvider {

    @FragmentScope
    @ContributesAndroidInjector(modules = BottomDrawerFragmentModule.class)
    public abstract BottomMenuFragment provideBottomDrawerFragmentFactory();

    @FragmentScope
    @ContributesAndroidInjector(modules = BuildRouteFragmentModule.class)
    public abstract BuildRouteFragment provideBuildRouteFragmentFactory();

    @ContributesAndroidInjector(modules = ShowRouteFragmentModule.class)
    abstract ShowRouteFragment provideShowRouteFragmentFactory();

    @ContributesAndroidInjector(modules = NavigateFragmentModule.class)
    abstract NavigatorFragment provideNavigatorFragmentFactory();

    @ContributesAndroidInjector(modules = MapFragmentModule.class)
    abstract MapFragment provideMapFragmentFactory();

    @FragmentScope
    @ContributesAndroidInjector(modules = FindPlaceFragmentModule.class)
    public abstract FindPlaceFragment provideFindPlaceFragmentFactory();

    @ContributesAndroidInjector(modules = ShowPlaceFragmentModule.class)
    abstract ShowPlaceFragment provideShowPlaceFragmentFactory();
}
