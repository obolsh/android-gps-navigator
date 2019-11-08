package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.impl.data.Route;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;
import gps.map.navigator.view.ui.fragment.listener.ISwipeRoute;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;

@Singleton
public class BuildRouteFragment extends AbstractNaviFragment implements ISwipeRoute, IPlacePickerCallback, ICachedPlaceCallback {
    @Inject
    Presenter presenter;
    @Inject
    Cache cache;
    @Inject
    DecorController decorController;
    @Inject
    IFragmentController<Fragment> fragmentController;
    @Inject
    IPlaceHistoryListener buildRouteCallback;
    @Inject
    AbstractAdapter adapter;
    @Inject
    Logger logger;
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
    @Nullable
    private IMapPlace originPlace;
    @Nullable
    private IMapPlace destinationPlace;
    @Nullable
    private TextView originTitle;
    @Nullable
    private TextView destinationTitle;

    @Inject
    public BuildRouteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_build_route, container, false);
        addHistoryPlacesToRecyclerView(view);
        presenter.buildRoute(buildRouteCallback);
        setupOrigin(view);
        setupDestination(view);
        setupSwipeOriginAndDestination(view);
        setupToolbarNavigation(view);
        return view;
    }

    private void addHistoryPlacesToRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.history_places_container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private LinearLayoutManager getLayoutManager() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        return manager;
    }

    private void setupOrigin(@NonNull View view) {
        originTitle = view.findViewById(R.id.origin_title);
        originTitle.setOnClickListener(originClickListener);
        setOriginPlace(getBestOrigin());
    }

    @Nullable
    private IMapPlace getBestOrigin() {
        IMapPlace lastOrigin = cache.getLastOrigin();
        if (lastOrigin != null) {
            return lastOrigin;
        } else {
            return cache.getMyLocation();
        }
    }

    private void setOriginPlace(@Nullable IMapPlace place) {
        if (place != null) {
            setOriginTitle(place.getTitle());
            originPlace = place;
            logger.debug("Set origin as: " + place);
        } else {
            setOriginTitle(getResources().getString(R.string.choose_origin_default));
            originPlace = null;
            logger.debug("Clean last origin");
        }
        cache.setLastOrigin(originPlace);
    }

    private void setOriginTitle(@NonNull String title) {
        if (originTitle != null) {
            originTitle.setText(title);
        }
    }

    private void setupDestination(@NonNull View view) {
        destinationTitle = view.findViewById(R.id.destination_title);
        destinationTitle.setOnClickListener(destinationClickListener);
        setDestinationPlace(getBestDestination());
    }

    @Nullable
    private IMapPlace getBestDestination() {
        IMapPlace lastDestination = cache.getLastDestination();
        if (lastDestination != null) {
            return lastDestination;
        } else {
            return null;
        }
    }

    private void setupSwipeOriginAndDestination(@NonNull View view) {
        ImageView button = view.findViewById(R.id.swipe_origin_and_destination_button);
        button.setOnClickListener(swipeListener);
    }

    private void setupToolbarNavigation(@NonNull View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(backPressListener);
    }

    @Override
    public void swipeOriginAndDestination() {
        IMapPlace lastOrigin = originPlace;
        IMapPlace lastDestination = destinationPlace;
        setOriginPlace(lastDestination);
        setDestinationPlace(lastOrigin);
    }

    @Override
    public void setOnlyOrigin(@NonNull IMapPlace origin) {
        cache.setLastOrigin(origin);
    }

    @Override
    public void setOnlyDestination(@NonNull IMapPlace destination) {
        cache.setLastDestination(destination);
    }

    private void setDestinationPlace(@Nullable IMapPlace place) {
        if (place != null) {
            setDestinationTitle(place.getTitle());
            destinationPlace = place;
            logger.debug("Set destination as: " + place);
        } else {
            setDestinationTitle(getResources().getString(R.string.choose_destination_default));
            destinationPlace = null;
            logger.debug("Clean last destination");
        }
        cache.setLastDestination(destinationPlace);
    }

    private void setDestinationTitle(@NonNull String title) {
        if (destinationTitle != null) {
            destinationTitle.setText(title);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndMoveFabToRight();
        openShowRouteFragmentIfRequied();
    }

    private void hideBottomBarAndMoveFabToRight() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(false);
        decorController.setShowMeOnMapFabVisibility(false);
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return BuildRouteFragment.class.getName();
    }

    @Override
    public void setHistoryPlaces(@NonNull List<IMapPlace> placeList) {
        if (adapter != null) {
            adapter.setInitialPlacesList(placeList);
        }
    }

    @Override
    public void setNewPickedPlace(@NonNull IMapPlace mapPlace) {
        if (originPlace == null) {
            setOriginPlace(mapPlace);
        } else if (destinationPlace == null) {
            setDestinationPlace(mapPlace);
        } else {
            logger.error("Can't use picked new place");
        }
        openShowRouteFragmentIfRequied();
    }

    @Override
    public void deleteHistoryPlace(int position, @NonNull IMapPlace mapPlace) {
        cache.removeHistoryPlace(mapPlace);
        adapter.removePlace(position, mapPlace);
    }

    @Override
    public void markAsFavouritePlace(@NonNull IMapPlace mapPlace) {
        setFavouriteState(mapPlace, true);
    }

    private void setFavouriteState(@NonNull IMapPlace mapPlace, boolean favourite) {
        List<IMapPlace> places = cache.getHistoryPlaces();
        if (places != null) {
            int position = getPosition(places, mapPlace);

            mapPlace.setFavourite(favourite);
            places.set(position, mapPlace);

            cache.setHistoryPlaces(places);
            adapter.updatePlace(mapPlace);
        }
    }

    @Override
    public void setNewFoundPlace(@NonNull IMapPlace mapPlace) {
        cache.addNewHistoryPlace(mapPlace);
    }

    @Override
    public void markAdNotFavouritePlace(@NonNull IMapPlace mapPlace) {
        setFavouriteState(mapPlace, false);
    }

    private int getPosition(@NonNull List<IMapPlace> places, @NonNull IMapPlace item) {
        for (int i = 0; i < places.size(); i++) {
            if (item.getId().equals(places.get(i).getId())) {
                return i;
            }
        }
        return 0;
    }

    private void openShowRouteFragmentIfRequied() {
        if (originPlace != null && destinationPlace != null) {
            fragmentController.removeFromBackStack(this);
            IRoute route = buildNewRoute();
            cache.setLastRoute(route);
            cache.setLastOrigin(null);
            cache.setLastDestination(null);
            fragmentController.openFragment(new ShowRouteFragment());
        }
    }

    @NonNull
    private IRoute buildNewRoute() {
        IMapPlace origin = cache.getLastOrigin();
        IMapPlace destination = cache.getLastDestination();
        return new Route("route_id", origin, destination, "Route", System.currentTimeMillis());
    }
}
