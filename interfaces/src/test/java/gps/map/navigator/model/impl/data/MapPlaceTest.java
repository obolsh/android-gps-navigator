package gps.map.navigator.model.impl.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapPlaceTest {
    @Test
    public void make_getId_verify() {
        MapPlace mapPlace = new MapPlace();
        mapPlace.setId("foo");

        assertEquals("foo", mapPlace.getId());
    }

    @Test
    public void make_getX_verify() {
        MapPlace mapPlace = new MapPlace();
        mapPlace.setLongitude(123d);

        assertEquals(123d, mapPlace.getLongitude(), 0);
    }

    @Test
    public void make_getY_verify() {
        MapPlace mapPlace = new MapPlace();
        mapPlace.setLatitude(1234d);

        assertEquals(1234d, mapPlace.getLatitude(),0);
    }

    @Test
    public void make_getTitle_verify() {
        MapPlace mapPlace = new MapPlace();
        mapPlace.setTitle("boo");

        assertEquals("boo", mapPlace.getTitle());
    }

    @Test
    public void make_getAdress_verify() {
        MapPlace mapPlace = new MapPlace();
        mapPlace.setAddress("doo");

        assertEquals("doo", mapPlace.getAddress());
    }

    @Test
    public void make_getLaber_verify() {
        MapPlace mapPlace = new MapPlace();
        mapPlace.setLabel("goo");

        assertEquals("goo", mapPlace.getLabel());
    }

    @Test
    public void make_getFavourite_verify() {
        MapPlace mapPlace = new MapPlace();
        mapPlace.setFavourite(true);

        assertTrue(mapPlace.isFavourite());
    }

    @Test
    public void make_getLastUserTime_verify() {
        MapPlace mapPlace = new MapPlace();
        mapPlace.setLastUsedTime(12345L);

        assertEquals(12345L, mapPlace.getLastUsedTime());
    }

    @Test
    public void make_getId_verify_constructor() {
        MapPlace mapPlace = new MapPlace("foo", 0,0,"","","",false,0);

        assertEquals("foo", mapPlace.getId());
    }

    @Test
    public void make_getX_verify_constructor() {
        MapPlace mapPlace = new MapPlace("", 12d,0,"","","",false,0);

        assertEquals(12d, mapPlace.getLongitude(),0);
    }

    @Test
    public void make_getY_verify_constructor() {
        MapPlace mapPlace = new MapPlace("", 0,123d,"","","",false,0);

        assertEquals(123d, mapPlace.getLatitude(),0);
    }

    @Test
    public void make_getTitle_verify_constructor() {
        MapPlace mapPlace = new MapPlace("", 0,0,"too","","",false,0);

        assertEquals("too", mapPlace.getTitle());
    }

    @Test
    public void make_getAdress_verify_constructor() {
        MapPlace mapPlace = new MapPlace("", 0,0,"","yoo","",false,0);

        assertEquals("yoo", mapPlace.getAddress());
    }

    @Test
    public void make_getLaber_verify_constructor() {
        MapPlace mapPlace = new MapPlace("", 0,0,"","","uoo",false,0);

        assertEquals("uoo", mapPlace.getLabel());
    }

    @Test
    public void make_getFavourite_verify_constructor() {
        MapPlace mapPlace = new MapPlace("", 0,0,"","","",true,0);

        assertTrue(mapPlace.isFavourite());
    }

    @Test
    public void make_getLastUserTime_verify_constructor() {
        MapPlace mapPlace = new MapPlace("foo", 0,0,"","","",false,123456L);

        assertEquals(123456L, mapPlace.getLastUsedTime());
    }
}