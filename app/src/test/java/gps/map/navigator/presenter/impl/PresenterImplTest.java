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

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        PresenterImpl.class, ShowMeOnMapListener.class,
        ShowRouteListener.class, FindPlaceListener.class, NavigateListener.class})
public class PresenterImplTest {

    private MapSdk mapSdk;
    private Cache cache;
    private IMapTypeController mapTypeController;
    private IRoute route;

    private ShowMeOnMapListener showMeOnMapListener;
    private ShowRouteListener showRouteListener;
    private FindPlaceListener findPlaceListener;
    private NavigateListener navigateListener;

    private IPlaceHistoryListener placeHistoryListener;
    private IPlaceListener placeListener;
    private IPlaceShowListener placeShowListener;
    private IRouteReadyListener routeReadyListener;
    private IRouteListener routeListener;

    private IMapPlace place;


    @Before
    public void setUp() throws Exception {
        mapSdk = mock(MapSdk.class);
        cache = mock(Cache.class);
        mapTypeController = mock(IMapTypeController.class);
        route = mock(IRoute.class);
        place = mock(IMapPlace.class);

        showMeOnMapListener = mock(ShowMeOnMapListener.class);
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
    public void receive_showMap_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.showMap();

        verify(mapSdk).showMap();
    }

    @Test
    public void receive_showPlace_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);

        presenter.showPlace(place, placeShowListener);

        verify(mapSdk).showPlace(eq(place), eq(placeShowListener));
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

        presenter.findPlace("foo", placeListener);

        verify(mapSdk).findPlace(eq("foo"), eq(findPlaceListener));
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

    @Test
    public void receive_hasTrafficMode_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);
        when(mapTypeController.hasTrafficMode()).thenReturn(true);

        boolean traffic_enabled = presenter.hasTrafficMode();

        assertTrue(traffic_enabled);
    }

    @Test
    public void receive_hasSatelliteMode_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);
        when(mapTypeController.hasSatelliteMode()).thenReturn(true);

        boolean satelliteMode_enabled = presenter.hasSatelliteMode();

        assertTrue(satelliteMode_enabled);
    }

    @Test
    public void receive_hasNightMode_verify() {
        PresenterImpl presenter = new PresenterImpl();
        initReferences(presenter);
        when(mapTypeController.hasNightMode()).thenReturn(true);

        boolean nightMode_enabled = presenter.hasNightMode();

        assertTrue(nightMode_enabled);
    }
}