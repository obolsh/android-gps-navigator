package gps.map.navigator.view.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomappbar.BottomAppBar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gps.map.navigator.R;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.ShowRouteCallback;

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
@PrepareForTest({ShowRouteFragment.class})
public class ShowRouteFragmentTest {

    private LayoutInflater inflater;
    private View view;
    private Presenter presenter;
    private DecorController decorController;
    private Toolbar toolbar;
    private IRoute route;
    private TextView originTitle;
    private TextView destinationTitle;
    private IMapPlace originPlace;
    private IMapPlace destinationPlace;
    private RelativeLayout routeContainer;
    private ImageView imageView;

    private View.OnClickListener backPressListener;
    private View.OnClickListener swipeListener;
    private View.OnClickListener originClickListener;
    private View.OnClickListener destinationClickListener;
    private View parentView;
    private IFragmentController fragmentController;

    @Before
    public void setUp() throws Exception {
        inflater = mock(LayoutInflater.class);
        view = mock(View.class);
        presenter = mock(Presenter.class);
        decorController = mock(DecorController.class);
        toolbar = mock(Toolbar.class);
        routeContainer = mock(RelativeLayout.class);
        originPlace = mock(IMapPlace.class);
        destinationPlace = mock(IMapPlace.class);
        originTitle = mock(TextView.class);
        destinationTitle = mock(TextView.class);
        backPressListener = mock(View.OnClickListener.class);
        swipeListener = mock(View.OnClickListener.class);
        originClickListener = mock(View.OnClickListener.class);
        destinationClickListener = mock(View.OnClickListener.class);
        imageView = mock(ImageView.class);
        route = mock(IRoute.class);
        parentView = mock(View.class);
        fragmentController = mock(IFragmentController.class);

        when(inflater.inflate(eq(R.layout.fragment_show_route), nullable(ViewGroup.class), anyBoolean())).thenReturn(view);
        when(inflater.inflate(eq(123), nullable(ViewGroup.class), anyBoolean())).thenReturn(parentView);
        when(originPlace.getTitle()).thenReturn("title1");
        when(destinationPlace.getTitle()).thenReturn("title2");
        when(presenter.getLastRoute()).thenReturn(route);
        when(route.getOrigin()).thenReturn(originPlace);
        when(route.getDestination()).thenReturn(destinationPlace);

        LinearLayoutManager linearLayoutManager = mock(LinearLayoutManager.class);
        whenNew(LinearLayoutManager.class).withAnyArguments().thenReturn(linearLayoutManager);
    }

    private ShowRouteFragment createFragment() {
        ShowRouteFragment fragment = new ShowRouteFragment();
        setInnerFields(fragment);
        setClickListeners(fragment);
        makeFoundViews();
        return fragment;
    }

    private void setInnerFields(Fragment fragment) {
        setInternalState(fragment, "presenter", presenter);
        setInternalState(fragment, "decorController", decorController);
        setInternalState(fragment, "fragmentController", fragmentController);
    }

    private void makeFoundViews() {
        when(view.findViewById(eq(R.id.route_container))).thenReturn(routeContainer);
        when(view.findViewById(eq(R.id.origin_title))).thenReturn(originTitle);
        when(view.findViewById(eq(R.id.destination_title))).thenReturn(destinationTitle);
        when(view.findViewById(eq(R.id.swipe_origin_and_destination_button))).thenReturn(imageView);
        when(view.findViewById(eq(R.id.toolbar))).thenReturn(toolbar);
    }

    private void setClickListeners(Fragment fragment) {
        setInternalState(fragment, "backPressListener", backPressListener);
        setInternalState(fragment, "swipeListener", swipeListener);
        setInternalState(fragment, "originClickListener", originClickListener);
        setInternalState(fragment, "destinationClickListener", destinationClickListener);
    }

    @Test
    public void make_onStart_verify() {
        ShowRouteFragment fragment = createFragment();

        fragment.onStart();

        verify(presenter).showRoute(eq(route), any(ShowRouteCallback.class));
    }

    @Test
    public void make_onCreateView_verify() {
        ShowRouteFragment fragment = createFragment();

        View created = fragment.onCreateView(inflater, null, null);

        assertSame(created, view);

        verify(routeContainer).addView(eq(parentView));

        verify(originTitle).setOnClickListener(eq(originClickListener));
        verify(originTitle).setText(eq("title1"));

        verify(destinationTitle).setOnClickListener(eq(destinationClickListener));
        verify(destinationTitle).setText(eq("title2"));

        verify(toolbar).setNavigationOnClickListener(eq(backPressListener));
        verify(imageView).setOnClickListener(eq(swipeListener));
    }

    @Test
    public void make_onActivityCreated_verify() {
        ShowRouteFragment fragment = createFragment();

        fragment.onActivityCreated(null);

        verify(decorController).setBottomBarVisibility(eq(false));
        verify(decorController).setFabVisibility(eq(true));
        verify(decorController).setShowMeOnMapFabVisibility(eq(false));
        verify(decorController).setFabAlignmentMode(eq(BottomAppBar.FAB_ALIGNMENT_MODE_END));
    }

    @Test
    public void make_swipeOriginAndDestination_verify() {
        ShowRouteFragment fragment = createFragment();
        IRoute newRoute = createRoute();
        when(presenter.getLastRoute()).thenReturn(newRoute);

        fragment.swipeOriginAndDestination();

        verify(presenter).setLastRoute(eq(newRoute));
        verify(newRoute).setOrigin(eq(destinationPlace));
        verify(newRoute).setDestination(eq(originPlace));
        verify(presenter).showRoute(eq(newRoute), any(ShowRouteCallback.class));
    }

    private IRoute createRoute() {
        IRoute route = mock(IRoute.class);
        when(route.getOrigin()).thenReturn(originPlace);
        when(route.getDestination()).thenReturn(destinationPlace);
        return route;
    }

    @Test
    public void make_setOnlyOrigin_verify() {
        ShowRouteFragment fragment = createFragment();
        IRoute newRoute = createRoute();
        when(presenter.getLastRoute()).thenReturn(newRoute);

        fragment.setOnlyOrigin(destinationPlace);

        verify(newRoute, times(0)).setDestination(any(IMapPlace.class));
        verify(newRoute).setOrigin(eq(destinationPlace));
    }

    @Test
    public void make_setOnlyDestination_verify() {
        ShowRouteFragment fragment = createFragment();
        IRoute newRoute = createRoute();
        when(presenter.getLastRoute()).thenReturn(newRoute);

        fragment.setOnlyDestination(originPlace);

        verify(newRoute, times(0)).setOrigin(any(IMapPlace.class));
        verify(newRoute).setDestination(eq(originPlace));
    }
}