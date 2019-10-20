package gps.map.navigator.view.viewmodel;

import android.view.View;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IViewInteraction;
import gps.map.navigator.view.viewmodel.callback.BuildRouteCallback;
import gps.map.navigator.view.viewmodel.callback.FindAndShowCallback;
import gps.map.navigator.view.viewmodel.callback.FindPlaceCallback;
import gps.map.navigator.view.viewmodel.callback.NavigateCallback;
import gps.map.navigator.view.viewmodel.callback.ShowMeOnMapCallback;

public class ViewInteractionImpl implements IViewInteraction {

    @Inject
    Presenter presenterStrategy;

    @Inject
    ViewInteractionImpl() {
    }

    @Override
    public void showMeOnMap(View view) {
        presenterStrategy.showMeOnMap(new ShowMeOnMapCallback());
    }

    @Override
    public void buildRoute() {
        presenterStrategy.buildRoute(new BuildRouteCallback());
    }

    @Override
    public void startNavigate(IRoute route) {
        presenterStrategy.navigate(route, new NavigateCallback());
    }

    @Override
    public void findPlaceAndShow() {
        presenterStrategy.findAndShowPlace(new FindAndShowCallback());
    }

    @Override
    public void findPlace() {
        presenterStrategy.findPlace(new FindPlaceCallback());
    }

    @Override
    public void enableTrafficMode(boolean enable) {
        presenterStrategy.enableTraffic(enable);
    }

    @Override
    public void enableNightMode(boolean enable) {
        presenterStrategy.enableNightMode(enable);
    }

    @Override
    public void enableSatelliteMode(boolean enable) {
        presenterStrategy.enableSatelliteMode(enable);
    }

    @Override
    public boolean hasTrafficMode() {
        return presenterStrategy.hasTrafficMode();
    }

    @Override
    public boolean hasNightMode() {
        return presenterStrategy.hasNightMode();
    }

    @Override
    public boolean hasSatelliteMode() {
        return presenterStrategy.hasSatelliteMode();
    }

    @Override
    public void backToMainScreen() {

    }

    @Override
    public void backToBuildRouteScreen() {
    }
}
