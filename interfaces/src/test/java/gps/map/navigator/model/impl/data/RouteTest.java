package gps.map.navigator.model.impl.data;

import org.junit.Before;
import org.junit.Test;

import gps.map.navigator.model.interfaces.IMapPlace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class RouteTest {
    private IMapPlace origin;
    private IMapPlace destination;

    @Before
    public void setUp() throws Exception {
        origin = mock(IMapPlace.class);
        destination = mock(IMapPlace.class);
    }

    @Test
    public void make_getId_verify() {
        Route route = new Route();
        route.setId("foo");

        assertEquals("foo", route.getId());
    }

    @Test
    public void make_getOrigin_verify() {
        Route route = new Route();
        route.setOrigin(origin);

        assertSame(origin, route.getOrigin());
    }

    @Test
    public void make_getDestination_verify() {
        Route route = new Route();
        route.setDestination(destination);

        assertSame(destination, route.getDestination());
    }

    @Test
    public void make_getTitle_verify() {
        Route route = new Route();
        route.setTitle("boo");

        assertEquals("boo", route.getTitle());
    }

    @Test
    public void make_getLastUsedTime_verify() {
        Route route = new Route();
        route.setLastUsedTime(123L);

        assertEquals(123L, route.getLastUsedTime());
    }

    @Test
    public void make_getId_verify_constructor() {
        Route route = new Route("foo", null, null, "", 0);

        assertEquals("foo", route.getId());
    }

    @Test
    public void make_getOrigin_verify_constructor() {
        Route route = new Route("", origin, null, "", 0);

        assertSame(origin, route.getOrigin());
    }

    @Test
    public void make_getDestination_verify_constructor() {
        Route route = new Route("", null, destination, "", 0);

        assertSame(destination, route.getDestination());
    }

    @Test
    public void make_getTitle_verify_constructor() {
        Route route = new Route("", null, null, "boo", 0);

        assertEquals("boo", route.getTitle());
    }

    @Test
    public void make_getLastUsedTime_verify_constructor() {
        Route route = new Route("", null, null, "", 123L);

        assertEquals(123L, route.getLastUsedTime());
    }
}