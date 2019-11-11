package gps.map.navigator.presenter.impl.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.getInternalState;

public class ShowRouteListenerTest {

    private Presenter cache;
    private IRouteReadyListener routeReadyListener;
    private IRoute route;

    @Before
    public void setUp() throws Exception {
        cache = mock(Presenter.class);
        routeReadyListener = mock(IRouteReadyListener.class);
        route = mock(IRoute.class);
    }

    @Test
    public void receive_onRouteReady_verify() {
        ShowRouteListener listener = new ShowRouteListener(cache, routeReadyListener);

        listener.onRouteReady(route);

        verify(cache).setLastRoute(eq(route));
        verify(routeReadyListener).onRouteReady(eq(route));

        boolean routeReadyListener_cleaned = getInternalState(listener, "listener") == null;
        boolean cache_cleaned = getInternalState(listener, "cache") == null;

        assertTrue(routeReadyListener_cleaned && cache_cleaned);
    }

    @Test
    public void receive_onRouteFailed_verify() {
        ShowRouteListener listener = new ShowRouteListener(cache, routeReadyListener);

        listener.onRouteFailed(new Exception(""));

        verify(routeReadyListener).onRouteFailed(any(Exception.class));

        boolean routeReadyListener_cleaned = getInternalState(listener, "listener") == null;
        boolean cache_cleaned = getInternalState(listener, "cache") == null;

        assertTrue(routeReadyListener_cleaned && cache_cleaned);
    }
}