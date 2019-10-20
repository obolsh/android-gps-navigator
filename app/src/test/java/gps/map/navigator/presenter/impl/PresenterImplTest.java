package gps.map.navigator.presenter.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.model.interfaces.MapSdk;
import gps.map.navigator.presenter.impl.listener.FindAndShowListener;
import gps.map.navigator.presenter.impl.listener.FindPlaceListener;
import gps.map.navigator.presenter.impl.listener.NavigateListener;
import gps.map.navigator.presenter.impl.listener.ShowMeOnMapListener;
import gps.map.navigator.presenter.impl.listener.ShowRouteListener;
import gps.map.navigator.presenter.interfaces.IMapTypeController;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.interfaces.IPlaceShowListener;
import gps.map.navigator.view.interfaces.IRouteListener;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        PresenterImpl.class, ShowMeOnMapListener.class, FindAndShowListener.class,
        ShowRouteListener.class, FindPlaceListener.class, NavigateListener.class})
public class PresenterImplTest {

    private MapSdk mapSdk;
    private Cache cache;
    private IMapTypeController mapTypeController;
    private IRoute route;

    private ShowMeOnMapListener showMeOnMapListener;
    private FindAndShowListener findAndShowListener;
    private ShowRouteListener showRouteListener;
    private FindPlaceListener findPlaceListener;
    private NavigateListener navigateListener;

    private IPlaceHistoryListener placeHistoryListener;
    private IPlaceListener placeListener;
    private IPlaceShowListener placeShowListener;
    private IRouteReadyListener routeReadyListener;
    private IRouteListener routeListener;


    @Before
    public void setUp() throws Exception {
        mapSdk = mock(MapSdk.class);
        cache = mock(Cache.class);
        mapTypeController = mock(IMapTypeController.class);
        route = mock(IRoute.class);


        showMeOnMapListener = mock(ShowMeOnMapListener.class);
        findAndShowListener = mock(FindAndShowListener.class);
        showRouteListener = mock(ShowRouteListener.class);
        findPlaceListener = mock(FindPlaceListener.class);
        navigateListener = mock(NavigateListener.class);


        placeHistoryListener = mock(IPlaceHistoryListener.class);
        placeListener = mock(IPlaceListener.class);
        placeShowListener = mock(IPlaceShowListener.class);
        routeReadyListener = mock(IRouteReadyListener.class);
        routeListener = mock(IRouteListener.class);

        whenNew(ShowMeOnMapListener.class)
                .withArguments(eq(cache), eq(placeListener))
                .thenReturn(showMeOnMapListener);
        whenNew(FindAndShowListener.class)
                .withArguments(eq(mapSdk), eq(cache), eq(placeShowListener))
                .thenReturn(findAndShowListener);
        whenNew(ShowRouteListener.class)
                .withArguments(eq(cache), eq(routeReadyListener))
                .thenReturn(showRouteListener);
        whenNew(FindPlaceListener.class)
                .withArguments(eq(cache), eq(placeListener))
                .thenReturn(findPlaceListener);
        whenNew(NavigateListener.class)
                .withArguments(eq(routeListener))
                .thenReturn(navigateListener);

    }

    private void initReferences(PresenterImpl presenter) {
        setInternalState(presenter, "mapSdk", mapSdk);
        setInternalState(presenter, "cache", cache);
        setInternalState(presenter, "mapTypeController", mapTypeController);
    }

    @Test
    public void receive_showMeOnMap_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.showMeOnMap(placeListener);

        verify(mapSdk).showMeOnMap(eq(showMeOnMapListener));
    }

    @Test
    public void receive_enableTraffic_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.enableTraffic(true);
        verify(mapTypeController).enableTraffic(eq(true));

        presenter.enableTraffic(false);
        verify(mapTypeController).enableTraffic(eq(false));
    }

    @Test
    public void receive_enableSateliteMode_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.enableSatelliteMode(true);
        verify(mapTypeController).enableSatellite(eq(true));

        presenter.enableSatelliteMode(false);
        verify(mapTypeController).enableSatellite(eq(false));
    }

    @Test
    public void receive_enableNightMode_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.enableNightMode(true);
        verify(mapTypeController).enableNightMode(eq(true));

        presenter.enableNightMode(false);
        verify(mapTypeController).enableNightMode(eq(false));
    }

    @Test
    public void receive_findAndShowPlace_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.findAndShowPlace(placeShowListener);

        verify(mapSdk).findPlace(eq(findAndShowListener));
    }

    @Test
    public void receive_showRoute_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.showRoute(route, routeReadyListener);

        verify(mapSdk).showRoute(eq(route), eq(showRouteListener));
    }

    @Test
    public void receive_findPlace_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.findPlace(placeListener);

        verify(mapSdk).findPlace(eq(findPlaceListener));
    }

    @Test
    public void receive_navigate_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.navigate(route, routeListener);

        verify(mapSdk).navigate(eq(route), eq(navigateListener));
    }

    @Test
    public void receive_buildRoute_verify_no_history() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.buildRoute(placeHistoryListener);

        verify(placeHistoryListener).onHistoryPlacesError(any(Exception.class));
    }

    @Test
    public void receive_buildRoute_verify_has_history() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        List<IMapPlace> mapPlaces = new ArrayList<>();
        mapPlaces.add(mock(IMapPlace.class));
        when(cache.getHistoryPlaces()).thenReturn(mapPlaces);

        presenter.buildRoute(placeHistoryListener);

        verify(placeHistoryListener).onHistoryPlacesFound(eq(mapPlaces));
    }
}