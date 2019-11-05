package gps.map.navigator.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.AndroidSupportInjection;
import demo.fragment.FragmentRoute;
import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.controller.IFragment;
import gps.map.navigator.view.ui.fragment.listener.ISwipeRoute;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.ShowRouteCallback;

public class ShowRouteFragment extends FragmentRoute implements IFragment<Fragment>, ISwipeRoute {

    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;
    @Inject
    Cache cache;
    @Inject
    @Named(Constants.BackPressListener)
    View.OnClickListener backPressListener;
    @Inject
    @Named(Constants.SwipePlacesListener)
    View.OnClickListener swipeListener;
    @Inject
    @Named(Constants.OriginClickListener)
    View.OnClickListener originClickListener;
    @Inject
    @Named(Constants.DestinationClickListener)
    View.OnClickListener destinationClickListener;
    private TextView originTitle;
    private TextView destinationTitle;

    @Inject
    public ShowRouteFragment() {
    }

    @Override
    public Fragment getInstance() {
        return this;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        IRoute route = cache.getLastRoute();
        showRouteOnMap(route);
    }

    private void showRouteOnMap(IRoute route) {
        presenter.showRoute(route, new ShowRouteCallback());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_route, container, false);
        RelativeLayout routeContainer = root.findViewById(R.id.route_container);
        View routeView = super.onCreateView(inflater, null, null);
        routeContainer.addView(routeView);
        setupOrigin(root);
        setupDestination(root);
        setupToolbarNavigation(root);
        setupSwipeOriginAndDestination(root);
        return root;
    }

    private void setupOrigin(View view) {
        originTitle = view.findViewById(R.id.origin_title);
        originTitle.setOnClickListener(originClickListener);
        setOriginTitle();
    }

    private void setOriginTitle() {
        originTitle.setText(getOriginTitle());
    }

    private String getOriginTitle() {
        return cache.getLastRoute().getOrigin().getTitle();
    }

    private void setupDestination(View view) {
        destinationTitle = view.findViewById(R.id.destination_title);
        destinationTitle.setOnClickListener(destinationClickListener);
        setDestinationTitle();
    }

    private void setDestinationTitle() {
        destinationTitle.setText(getDestinationTitle());
    }

    private String getDestinationTitle() {
        return cache.getLastRoute().getDestination().getTitle();
    }

    private void setupSwipeOriginAndDestination(View view) {
        ImageView button = view.findViewById(R.id.swipe_origin_and_destination_button);
        button.setOnClickListener(swipeListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndMoveFabToRight();
    }

    private void hideBottomBarAndMoveFabToRight() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(true);
        decorController.setShowMeOnMapFabVisibility(true);
        decorController.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
    }

    @Override
    public String getFragmentTag() {
        return ShowRouteFragment.class.getName();
    }

    private void setupToolbarNavigation(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(backPressListener);
    }

    @Override
    public void swipeOriginAndDestination() {
        IRoute route = cache.getLastRoute();
        IMapPlace newOrigin = route.getDestination();
        IMapPlace newDestination = route.getOrigin();

        changeActiveRoute(newOrigin, newDestination);
    }

    @Override
    public void setOnlyOrigin(IMapPlace origin) {
        changeActiveRoute(origin, null);
    }

    @Override
    public void setOnlyDestination(IMapPlace destination) {
        changeActiveRoute(null, destination);
    }

    private void changeActiveRoute(IMapPlace newOrigin, IMapPlace newDestination) {
        IRoute route = cache.getLastRoute();
        if (newOrigin != null) {
            route.setOrigin(newOrigin);
        }
        if (newDestination != null) {
            route.setDestination(newDestination);
        }

        cache.setLastRoute(route);

        if (newOrigin != null) {
            setOriginTitle();
        }

        if (newDestination != null) {
            setDestinationTitle();
        }

        showRouteOnMap(route);
    }
}
