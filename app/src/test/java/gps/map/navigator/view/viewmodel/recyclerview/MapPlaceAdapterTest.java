package gps.map.navigator.view.viewmodel.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.R;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MapPlaceAdapter.class, LayoutInflater.class, MapPlaceViewHolder.class})
public class MapPlaceAdapterTest {

    private IPlacePickerCallback fragment;
    private MapPlaceViewHolder holder;
    private ViewGroup parent;

    @Before
    public void setUp() throws Exception {
        mockStatic(LayoutInflater.class);
        fragment = mock(IPlacePickerCallback.class);
        holder = mock(MapPlaceViewHolder.class);
        parent = mock(ViewGroup.class);
        View root = mock(View.class);

        whenNew(MapPlaceViewHolder.class).withAnyArguments().thenReturn(holder);
        LayoutInflater inflater = mock(LayoutInflater.class);
        when(LayoutInflater.from(any(Context.class))).thenReturn(inflater);
        when(parent.getContext()).thenReturn(mock(Context.class));
        when(inflater.inflate(eq(R.layout.map_place_view), eq(parent))).thenReturn(root);

    }

    List<IMapPlace> getPlaces() {
        List<IMapPlace> places = new ArrayList<>();
        places.add(getOrigin());
        places.add(getDestination());
        places.add(getMyLocation());
        return places;
    }

    IMapPlace getOrigin() {
        IMapPlace place = mock(IMapPlace.class);
        when(place.getId()).thenReturn("origin");
        when(place.getLastUsedTime()).thenReturn(1L);
        return place;
    }

    IMapPlace getDestination() {
        IMapPlace place = mock(IMapPlace.class);
        when(place.getId()).thenReturn("destination");
        when(place.getLastUsedTime()).thenReturn(2L);
        return place;
    }

    IMapPlace getMyLocation() {
        IMapPlace place = mock(IMapPlace.class);
        when(place.getId()).thenReturn("my_location");
        when(place.getLastUsedTime()).thenReturn(3L);
        return place;
    }

    private MapPlaceAdapter createAdapter() {
        MapPlaceAdapter adapter = new MapPlaceAdapter();
        setInternalState(adapter, "fragment", fragment);
        setInternalState(adapter, "logger", mock(Logger.class));
        return adapter;
    }

    @Test
    public void make_onCreateViewHolder_verify() {
        MapPlaceAdapter adapter = createAdapter();

        MapPlaceViewHolder created = adapter.onCreateViewHolder(parent, 0);

        assertSame(holder, created);
    }

    @Test
    public void make_onBindViewHolder_verify() {
        MapPlaceAdapter adapter = createAdapter();
        List<IMapPlace> places = new ArrayList<>();
        IMapPlace origin = getOrigin();
        places.add(origin);

        adapter.onBindViewHolder(holder, 0);
        adapter.changePlacesList(places);
        adapter.onBindViewHolder(holder, 0);

        verify(holder).setMapPlace(eq(origin));
    }

    @Test
    public void make_getItemCount_verify() {
        MapPlaceAdapter adapter = createAdapter();

        boolean was_empty = adapter.getItemCount() == 0;
        adapter.changePlacesList(getPlaces());
        boolean has_three = adapter.getItemCount() == 3;

        assertTrue(was_empty && has_three);
    }

    @Test
    public void make_getOriginalPlacesList_verify() {
        MapPlaceAdapter adapter = createAdapter();
        List<IMapPlace> places = getPlaces();
        adapter.setInitialPlacesList(places);

        List<IMapPlace> cached = adapter.getOriginalPlacesList();

        assertSame(places, cached);
    }

    @Test
    public void make_changePlacesList_verify() {
        MapPlaceAdapter adapter = createAdapter();
        List<IMapPlace> places = getPlaces();

        boolean was_empty = adapter.getItemCount() == 0;
        adapter.changePlacesList(places);
        boolean has_three = adapter.getItemCount() == 3;

        assertTrue(was_empty && has_three);
    }

    @Test
    public void make_showSinglePlace_verify() {
        MapPlaceAdapter adapter = createAdapter();
        IMapPlace origin = getOrigin();

        adapter.showSinglePlace(origin);
        boolean cache_not_empty = adapter.getItemCount() == 1;
        boolean origin_not_empty = adapter.getOriginalPlacesList().size() == 1;

        verify(fragment).setNewFoundPlace(eq(origin));
        assertTrue(cache_not_empty && origin_not_empty);
    }

    @Test
    public void make_showFoundedPlacesList_verify() {
        MapPlaceAdapter adapter = createAdapter();
        List<IMapPlace> places = getPlaces();

        adapter.showFoundedPlacesList(places);

        boolean cache_not_empty = adapter.getItemCount() == 3;

        assertTrue(cache_not_empty);
    }

    @Test
    public void make_removePlace_verify() {
        MapPlaceAdapter adapter = createAdapter();
        List<IMapPlace> places = getPlaces();
        adapter.setInitialPlacesList(places);
        IMapPlace origin = getOrigin();

        adapter.removePlace(0, origin);

        boolean cache_changed = adapter.getItemCount() == 2;
        boolean origin_changed = adapter.getOriginalPlacesList().size() == 2;

        assertTrue(cache_changed && origin_changed);
    }

    @Test
    public void make_updatePlace_verify() {
        MapPlaceAdapter adapter = createAdapter();
        List<IMapPlace> places = getPlaces();
        adapter.setInitialPlacesList(places);

        IMapPlace origin = getOrigin();
        when(origin.getAddress()).thenReturn("faa");

        adapter.updatePlace(origin);

        boolean has_address = adapter.getOriginalPlacesList().get(2).getAddress().equals("faa");

        assertTrue(has_address);
    }

    @Test
    public void make_setInitialPlacesList_verify() {
        MapPlaceAdapter adapter = createAdapter();
        List<IMapPlace> places = getPlaces();

        adapter.setInitialPlacesList(places);

        boolean cache_changed = adapter.getItemCount() == 3;
        boolean origin_changed = adapter.getOriginalPlacesList().size() == 3;

        assertTrue(cache_changed && origin_changed);
    }
}