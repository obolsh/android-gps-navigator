package gps.map.navigator.presenter.impl.listener;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IRouteListener;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.getInternalState;

public class NavigateListenerTest {

    private IRouteListener routeListener;
    private IRoute route;

    @Before
    public void setUp() throws Exception {
        route = mock(IRoute.class);
        routeListener = mock(IRouteListener.class);
    }

    @Test
    public void receive_onRouteStarted_verify() {
        NavigateListener listener = new NavigateListener(routeListener);

        listener.onRouteStarted(route);

        verify(routeListener).onRouteStarted(eq(route));
    }

    @Test
    public void receive_onRouteStopped_verify() {
        NavigateListener listener = new NavigateListener(routeListener);

        listener.onRouteStopped(route);

        verify(routeListener).onRouteStopped(eq(route));

        assertNull(getInternalState(listener, "listener"));
    }

    @Test
    public void receive_onRouteError_verify() {
        NavigateListener listener = new NavigateListener(routeListener);

        listener.onRouteError(route, new Exception(""));

        verify(routeListener).onRouteError(eq(route), any(Exception.class));

        assertNull(getInternalState(listener, "listener"));
    }
}