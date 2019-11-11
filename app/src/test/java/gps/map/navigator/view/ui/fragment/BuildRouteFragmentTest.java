package gps.map.navigator.view.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BuildRouteFragment.class})

public class BuildRouteFragmentTest {
    private LayoutInflater inflater;
    private View view;
    private Presenter presenter;
    private DecorController decorController;
    private IFragmentController<Fragment> fragmentController;
    private AbstractAdapter adapter;
    private IMapPlace originPlace;
    private IMapPlace destinationPlace;
    private TextView originTitle;
    private TextView destinationTitle;

    private IPlaceHistoryListener buildRouteCallback;
    private RecyclerView recyclerView;
    private ImageView button;
    private Toolbar toolbar;

    private View.OnClickListener backPressListener;
    private View.OnClickListener swipeListener;
    private View.OnClickListener originClickListener;
    private View.OnClickListener destinationClickListener;
    private Context context;
    private ShowRouteFragment showRouteFragment;

    @Before
    public void setUp() throws Exception {
        context = mock(Context.class);
        inflater = mock(LayoutInflater.class);
        view = mock(View.class);
        presenter = mock(Presenter.class);
        decorController = mock(DecorController.class);
        fragmentController = mock(IFragmentController.class);
        adapter = mock(AbstractAdapter.class);
        recyclerView = mock(RecyclerView.class);
        Resources resources = mock(Resources.class);
        button = mock(ImageView.class);
        toolbar = mock(Toolbar.class);
        LinearLayoutManager linearLayoutManager = mock(LinearLayoutManager.class);

        originPlace = mock(IMapPlace.class);
        destinationPlace = mock(IMapPlace.class);

        originTitle = mock(TextView.class);
        destinationTitle = mock(TextView.class);

        buildRouteCallback = mock(IPlaceHistoryListener.class);

        backPressListener = mock(View.OnClickListener.class);
        swipeListener = mock(View.OnClickListener.class);
        originClickListener = mock(View.OnClickListener.class);
        destinationClickListener = mock(View.OnClickListener.class);
        showRouteFragment = mock(ShowRouteFragment.class);

        when(inflater.inflate(eq(R.layout.fragment_build_route), nullable(ViewGroup.class), anyBoolean())).thenReturn(view);
        when(originPlace.getTitle()).thenReturn("title1");
        when(destinationPlace.getTitle()).thenReturn("title2");

        when(context.getResources()).thenReturn(resources);
        when(resources.getString(eq(R.string.choose_destination_default))).thenReturn("destination");
        when(resources.getString(eq(R.string.choose_origin_default))).thenReturn("origin");

        whenNew(LinearLayoutManager.class).withAnyArguments().thenReturn(linearLayoutManager);
        whenNew(ShowRouteFragment.class).withAnyArguments().thenReturn(showRouteFragment);
    }

    private BuildRouteFragment createFragment() {
        BuildRouteFragment fragment = new BuildRouteFragment();
        setInnerFields(fragment);
        setClickListeners(fragment);
        makeFoundViews();
        return fragment;
    }

    private void setInnerFields(BuildRouteFragment fragment) {
        setInternalState(fragment, "presenter", presenter);
        setInternalState(fragment, "decorController", decorController);
        setInternalState(fragment, "adapter", adapter);
        setInternalState(fragment, "fragmentController", fragmentController);
        setInternalState(fragment, "logger", mock(Logger.class));
        setInternalState(fragment, "context", context);
        setInternalState(fragment, "buildRouteCallback", buildRouteCallback);
    }

    private void makeFoundViews() {
        when(view.findViewById(eq(R.id.history_places_container))).thenReturn(recyclerView);
        when(view.findViewById(eq(R.id.origin_title))).thenReturn(originTitle);
        when(view.findViewById(eq(R.id.destination_title))).thenReturn(destinationTitle);
        when(view.findViewById(eq(R.id.swipe_origin_and_destination_button))).thenReturn(button);
        when(view.findViewById(eq(R.id.toolbar))).thenReturn(toolbar);
    }

    private void setOriginAndDestinationTitle() {
        when(presenter.getLastOrigin()).thenReturn(originPlace);
        when(presenter.getLastDestination()).thenReturn(destinationPlace);
    }

    private void setClickListeners(BuildRouteFragment fragment) {
        setInternalState(fragment, "backPressListener", backPressListener);
        setInternalState(fragment, "swipeListener", swipeListener);
        setInternalState(fragment, "originClickListener", originClickListener);
        setInternalState(fragment, "destinationClickListener", destinationClickListener);
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
    public void make_onCreateView_no_history_places_verify() {
        BuildRouteFragment fragment = createFragment();

        View created = fragment.onCreateView(inflater, null, null);

        assertSame(created, view);

        verify(recyclerView).setAdapter(eq(adapter));
        verify(toolbar).setNavigationOnClickListener(eq(backPressListener));

        verify(originTitle).setOnClickListener(eq(originClickListener));
        verify(destinationTitle).setOnClickListener(eq(destinationClickListener));

        verify(originTitle).setText(eq("origin"));
        verify(destinationTitle).setText(eq("destination"));

        verify(presenter).setLastOrigin(nullable(IMapPlace.class));
        verify(presenter).setLastDestination(nullable(IMapPlace.class));

        verify(presenter).buildRoute(eq(buildRouteCallback));
    }

    @Test
    public void make_onCreateView_has_history_places_verify() {
        BuildRouteFragment fragment = createFragment();
        setOriginAndDestinationTitle();

        View created = fragment.onCreateView(inflater, null, null);

        assertSame(created, view);

        verify(recyclerView).setAdapter(eq(adapter));
        verify(toolbar).setNavigationOnClickListener(eq(backPressListener));

        verify(originTitle).setOnClickListener(eq(originClickListener));
        verify(destinationTitle).setOnClickListener(eq(destinationClickListener));

        verify(originTitle).setText(eq("title1"));
        verify(destinationTitle).setText(eq("title2"));

        verify(presenter).setLastOrigin(eq(originPlace));
        verify(presenter).setLastDestination(eq(destinationPlace));

        verify(presenter).buildRoute(eq(buildRouteCallback));
    }

    @Test
    public void make_swipeOriginAndDestination_verify() {
        BuildRouteFragment fragment = createFragment();
        setOriginAndDestinationTitle();
        fragment.onCreateView(inflater, null, null);

        fragment.swipeOriginAndDestination();

        verify(presenter).setLastOrigin(eq(destinationPlace));
        verify(presenter).setLastDestination(eq(originPlace));
        verify(originTitle).setText(eq("title2"));
        verify(destinationTitle).setText(eq("title1"));
    }

    @Test
    public void make_setOnlyOrigin_verify() {
        BuildRouteFragment fragment = createFragment();

        fragment.setOnlyOrigin(destinationPlace);

        verify(presenter).setLastOrigin(eq(destinationPlace));
    }

    @Test
    public void make_setOnlyDestination_verify() {
        BuildRouteFragment fragment = createFragment();

        fragment.setOnlyDestination(originPlace);

        verify(presenter).setLastDestination(eq(originPlace));
    }

    @Test
    public void make_onActivityCreated_verify() {
        BuildRouteFragment fragment = createFragment();
        setOriginAndDestinationTitle();
        fragment.onCreateView(inflater, null, null);

        fragment.onActivityCreated(null);

        verify(decorController).setBottomBarVisibility(eq(false));
        verify(decorController).setFabVisibility(eq(false));
        verify(decorController).setShowMeOnMapFabVisibility(eq(false));

        verify(fragmentController).removeFromBackStack(eq(fragment));
        verify(presenter, times(2)).setLastOrigin(nullable(IMapPlace.class));
        verify(presenter, times(2)).setLastDestination(nullable(IMapPlace.class));
        verify(presenter).setLastRoute(any(IRoute.class));

        verify(fragmentController).openFragment(eq(showRouteFragment));
    }

    @Test
    public void make_setHistoryPlaces_verify() {
        BuildRouteFragment fragment = createFragment();

        List<IMapPlace> places = getPlaces();
        fragment.setHistoryPlaces(places);

        verify(adapter).setInitialPlacesList(eq(places));
    }

    @Test
    public void make_setNewPickedPlace_verify_origin() {
        BuildRouteFragment fragment = createFragment();

        IMapPlace place = getOriginPlace();
        fragment.setNewPickedPlace(place);

        verify(presenter).setLastOrigin(eq(place));
    }

    @Test
    public void make_setNewPickedPlace_verify_destination() {
        BuildRouteFragment fragment = createFragment();
        setInternalState(fragment, "originPlace", originPlace);

        IMapPlace place = getOriginPlace();
        fragment.setNewPickedPlace(place);

        verify(presenter).setLastDestination(eq(place));
    }

    @Test
    public void make_deleteHistoryPlace_verify() {
        BuildRouteFragment fragment = createFragment();

        IMapPlace place = getOriginPlace();
        fragment.deleteHistoryPlace(10, place);

        verify(presenter).removeHistoryPlace(eq(place));
        verify(adapter).removePlace(eq(10), eq(place));
    }

    @Test
    public void make_markAsFavouritePlace_verify() {
        BuildRouteFragment fragment = createFragment();
        List<IMapPlace> places = getPlaces();
        when(presenter.getHistoryPlaces()).thenReturn(places);

        IMapPlace place = getOriginPlace();
        fragment.markAsFavouritePlace(place);

        verify(place).setFavourite(eq(true));
        verify(presenter).setHistoryPlaces(eq(places));
        verify(adapter).updatePlace(eq(place));
    }

    @Test
    public void make_setNewFoundPlace_verify() {
        BuildRouteFragment fragment = createFragment();

        IMapPlace place = getOriginPlace();
        fragment.setNewFoundPlace(place);

        verify(presenter).addNewHistoryPlace(eq(place));
    }

    @Test
    public void make_markAdNotFavouritePlace_verify() {
        BuildRouteFragment fragment = createFragment();
        List<IMapPlace> places = getPlaces();
        when(presenter.getHistoryPlaces()).thenReturn(places);

        IMapPlace place = getOriginPlace();
        fragment.markAdNotFavouritePlace(place);

        verify(place).setFavourite(eq(false));
        verify(presenter).setHistoryPlaces(eq(places));
        verify(adapter).updatePlace(eq(place));
    }
}