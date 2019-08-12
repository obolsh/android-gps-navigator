package gps.map.navigator.presenter.strategy;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.impl.PresenterImpl;
import gps.map.navigator.presenter.interfaces.IPresenter;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class PresenterStrategy implements IPresenter {

    private static final IPresenter DEFAULT_STRATEGY = new PresenterImpl();
    private static IPresenter activeStrategy;
    private static final PresenterStrategy instance = new PresenterStrategy();

    private PresenterStrategy() {
        activeStrategy = DEFAULT_STRATEGY;
    }

    public static PresenterStrategy getInstance() {
        return instance;
    }

    public void setStrategy(IPresenter presenterStrategy) {
        if (presenterStrategy != null) {
            activeStrategy = presenterStrategy;
        } else {
            activeStrategy = DEFAULT_STRATEGY;
        }
    }

    @Override
    public void showMeOnMap(IPlaceListener placeListener) {
        activeStrategy.showMeOnMap(placeListener);
    }

    @Override
    public void enableTraffic(boolean enable) {
        activeStrategy.enableTraffic(enable);
    }

    @Override
    public void enableNightMode(boolean enable) {
        activeStrategy.enableNightMode(enable);
    }

    @Override
    public void findAndShowPlace(IPlaceShowListener placeShowListener) {
        activeStrategy.findAndShowPlace(placeShowListener);
    }

    @Override
    public void showRoute(IRoute route, IRouteReadyListener routeReadyListener) {
        activeStrategy.showRoute(route, routeReadyListener);
    }

    @Override
    public void findPlace(IPlaceListener placeListener) {
        activeStrategy.findPlace(placeListener);
    }

    @Override
    public void navigate(IRoute route, IRouteListener routeListener) {
        activeStrategy.navigate(route, routeListener);
    }

    @Override
    public void buildRoute(IPlaceHistoryListener placeHistoryListener) {
        activeStrategy.buildRoute(placeHistoryListener);
    }
}
