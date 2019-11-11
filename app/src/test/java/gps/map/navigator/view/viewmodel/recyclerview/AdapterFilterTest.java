package gps.map.navigator.view.viewmodel.recyclerview;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AdapterFilter.class})
public class AdapterFilterTest {

    private AbstractAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = mock(AbstractAdapter.class);
        AdapterFilter.Result result = mock(AdapterFilter.Result.class);

        whenNew(AdapterFilter.Result.class).withAnyArguments().thenReturn(result);
    }

    private List<IMapPlace> getPlaces() {
        List<IMapPlace> places = new ArrayList<>();
        places.add(getOrigin());
        places.add(getDestination());
        return places;
    }

    private IMapPlace getOrigin() {
        IMapPlace place = mock(IMapPlace.class);
        when(place.getTitle()).thenReturn("foo");
        when(place.getAddress()).thenReturn("baa");
        return place;
    }

    private IMapPlace getDestination() {
        IMapPlace place = mock(IMapPlace.class);
        when(place.getTitle()).thenReturn("faa");
        when(place.getAddress()).thenReturn("gtt");
        return place;
    }

    @Test
    public void make_publishResults_verify() {
        AdapterFilter filter = new AdapterFilter(adapter);
        List<IMapPlace> places = getPlaces();
        when(adapter.getOriginalPlacesList()).thenReturn(places);
        AdapterFilter.Result adapterResult = (AdapterFilter.Result) filter.performFiltering("ba");

        filter.publishResults("ba", adapterResult);

        verify(adapter).changePlacesList(any(List.class));
        verify(adapter).notifyDataSetChanged();
    }

    @Test
    public void make_performFiltering_empty_query_verify() {
        AdapterFilter filter = new AdapterFilter(adapter);
        List<IMapPlace> places = getPlaces();
        when(adapter.getOriginalPlacesList()).thenReturn(places);

        AdapterFilter.Result adapterResult = (AdapterFilter.Result) filter.performFiltering("");
        int size = ((List<IMapPlace>) adapterResult.values).size();

        assertSame(places, adapterResult.values);
        assertEquals(2, size);
    }

    @Test
    public void make_performFiltering_check_mathTitle() {
        AdapterFilter filter = new AdapterFilter(adapter);
        List<IMapPlace> places = getPlaces();
        when(adapter.getOriginalPlacesList()).thenReturn(places);

        AdapterFilter.Result adapterResult = (AdapterFilter.Result) filter.performFiltering("fo");

        int size = ((List<IMapPlace>) adapterResult.values).size();
        assertEquals(1, size);

    }

    @Test
    public void make_performFiltering_check_mathAddress() {
        AdapterFilter filter = new AdapterFilter(adapter);
        List<IMapPlace> places = getPlaces();
        when(adapter.getOriginalPlacesList()).thenReturn(places);

        AdapterFilter.Result adapterResult = (AdapterFilter.Result) filter.performFiltering("ba");

        int size = ((List<IMapPlace>) adapterResult.values).size();
        assertEquals(1, size);

    }
}