package gps.map.navigator.view.ui.fragment;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FindPlaceFragment.class})
public class FindPlaceFragmentTest {

    private Activity activity;
    private LayoutInflater inflater;
    private View view;
    private Presenter presenter;
    private DecorController decorController;
    private IFragmentController<Fragment> fragmentController;
    private AbstractAdapter adapter;
    private IPlaceHistoryListener buildRouteCallback;
    private SearchView searchView;
    private Toolbar toolbar;
    private View.OnClickListener backPressListener;
    private SearchView.OnQueryTextListener searchListener;
    private ShowPlaceFragment showRouteFragment;
    private PlaceProxyListener listener;
    private RecyclerView recyclerView;

    @Before
    public void setUp() throws Exception {
        activity = mock(Activity.class);
        inflater = mock(LayoutInflater.class);
        view = mock(View.class);
        presenter = mock(Presenter.class);
        decorController = mock(DecorController.class);
        fragmentController = mock(IFragmentController.class);
        adapter = mock(AbstractAdapter.class);
        searchView = mock(SearchView.class);
        recyclerView = mock(RecyclerView.class);
        toolbar = mock(Toolbar.class);
        buildRouteCallback = mock(IPlaceHistoryListener.class);
        backPressListener = mock(View.OnClickListener.class);
        showRouteFragment = mock(ShowPlaceFragment.class);
        listener = mock(PlaceProxyListener.class);
        searchListener = mock(SearchView.OnQueryTextListener.class);

        when(inflater.inflate(eq(R.layout.find_place_fragment), nullable(ViewGroup.class), anyBoolean())).thenReturn(view);

        LinearLayoutManager linearLayoutManager = mock(LinearLayoutManager.class);
        whenNew(LinearLayoutManager.class).withAnyArguments().thenReturn(linearLayoutManager);
        whenNew(ShowPlaceFragment.class).withAnyArguments().thenReturn(showRouteFragment);
    }

    private void makeFoundViews() {
        when(view.findViewById(eq(R.id.history_places_container))).thenReturn(recyclerView);
        when(view.findViewById(eq(R.id.search_view_box))).thenReturn(searchView);
        when(view.findViewById(eq(R.id.toolbar))).thenReturn(toolbar);
    }

    private FindPlaceFragment createFragment() {
        FindPlaceFragment fragment = new FindPlaceFragment();
        setInnerFields(fragment);
        makeFoundViews();
        return fragment;
    }

    private void setInnerFields(FindPlaceFragment fragment) {
        setInternalState(fragment, "presenter", presenter);
        setInternalState(fragment, "decorController", decorController);
        setInternalState(fragment, "adapter", adapter);
        setInternalState(fragment, "fragmentController", fragmentController);
        setInternalState(fragment, "activity", activity);
        setInternalState(fragment, "buildRouteCallback", buildRouteCallback);
        setInternalState(fragment, "searchListener", searchListener);
        setInternalState(fragment, "backPressListener", backPressListener);
    }

    private List<IMapPlace> getPlaces() {
        List<IMapPlace> places = new ArrayList<>();
        places.add(getOriginPlace());
        return places;
    }

    private IMapPlace getOriginPlace() {
        IMapPlace mapPlace = mock(IMapPlace.class);
        when(mapPlace.getId()).thenReturn("origin");
        return mapPlace;
    }

    @Test
    public void make_onCreateView_verify() {
        FindPlaceFragment fragment = createFragment();

        View created = fragment.onCreateView(inflater, null, null);

        assertSame(view, created);

        verify(recyclerView).setAdapter(eq(adapter));
        verify(searchView).setOnQueryTextListener(eq(searchListener));
        verify(toolbar).setNavigationOnClickListener(eq(backPressListener));
        verify(presenter).buildRoute(eq(buildRouteCallback));
    }

    @Test
    public void make_onActivityCreated_verify() {
        FindPlaceFragment fragment = createFragment();
        fragment.onCreateView(inflater, null, null);

        fragment.onActivityCreated(null);

        verify(decorController).setFabVisibility(false);
        verify(decorController).setBottomBarVisibility(false);
        verify(decorController).setShowMeOnMapFabVisibility(false);

//        verify(searchView).requestFocus();
//        verify(searchView).setIconified(eq(false));
    }

    @Test
    public void make_setTask_check_match() {
        FindPlaceFragment fragment = createFragment();

        fragment.setTask(listener);

        PlaceProxyListener inner_listener = getInternalState(fragment, "listener");

        assertSame(listener, inner_listener);
    }

    @Test
    public void make_setNewPickedPlace_verify_listener() {
        FindPlaceFragment fragment = createFragment();
        fragment.setTask(listener);

        IMapPlace place = getOriginPlace();
        fragment.setNewPickedPlace(place);

        verify(listener).onPlaceLocated(eq(place));
        verify(activity).onBackPressed();
    }

    @Test
    public void make_setNewPickedPlace_verify_show_place_opened() {
        FindPlaceFragment fragment = createFragment();
        fragment.setTask(null);

        IMapPlace place = getOriginPlace();
        fragment.setNewPickedPlace(place);

        verify(presenter).setLastPlace(eq(place));
        verify(fragmentController).removeFromBackStack(eq(fragment));
        verify(fragmentController).openFragment(eq(showRouteFragment));
    }

    @Test
    public void make_deleteHistoryPlace_verify() {
        FindPlaceFragment fragment = createFragment();

        IMapPlace place = getOriginPlace();
        fragment.deleteHistoryPlace(10, place);

        verify(presenter).removeHistoryPlace(eq(place));
        verify(adapter).removePlace(eq(10), eq(place));
    }

    @Test
    public void make_markAsFavouritePlace_verify() {
        FindPlaceFragment fragment = createFragment();
        List<IMapPlace> places = getPlaces();
        when(presenter.getHistoryPlaces()).thenReturn(places);

        IMapPlace place = getOriginPlace();
        fragment.markAsFavouritePlace(place);

        verify(place).setFavourite(eq(true));
        verify(presenter).setHistoryPlaces(eq(places));
        verify(adapter).updatePlace(eq(place));
    }

    @Test
    public void make_markAdNotFavouritePlace_verify() {
        FindPlaceFragment fragment = createFragment();
        List<IMapPlace> places = getPlaces();
        when(presenter.getHistoryPlaces()).thenReturn(places);

        IMapPlace place = getOriginPlace();
        fragment.markAdNotFavouritePlace(place);

        verify(place).setFavourite(eq(false));
        verify(presenter).setHistoryPlaces(eq(places));
        verify(adapter).updatePlace(eq(place));
    }

    @Test
    public void make_setNewFoundPlace_verify() {
        FindPlaceFragment fragment = createFragment();

        IMapPlace place = getOriginPlace();
        fragment.setNewFoundPlace(place);

        verify(presenter).addNewHistoryPlace(eq(place));
    }

    @Test
    public void make_setHistoryPlaces_verify() {
        FindPlaceFragment fragment = createFragment();

        List<IMapPlace> places = getPlaces();
        fragment.setHistoryPlaces(places);

        verify(adapter).setInitialPlacesList(eq(places));
    }
}