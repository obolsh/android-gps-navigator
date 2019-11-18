package gps.map.navigator.view.ui.fragment;

import android.content.Context;
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
    private TextView originTitle;
    @Nullable
    private TextView destinationTitle;
    @Inject
    @Named(Constants.ApplicationContext)
    Context context;

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
        logger.debug("BuildRouteFragment onCreateView");
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
        LinearLayoutManager manager = new LinearLayoutManager(context);
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
        IMapPlace lastOrigin = presenter.getLastOrigin();
        if (lastOrigin != null) {
            return lastOrigin;
        } else {
            return presenter.getMyLocation();
        }
    }

    private void setOriginPlace(@Nullable IMapPlace place) {
        if (place != null
                && (presenter.getLastOrigin() == null
                || !placesAreTheSame(place, presenter.getLastDestination()))) {
            setOriginTitle(place.getTitle());
            presenter.setLastOrigin(place);
            logger.debug("Set origin as: " + place);
        } else {
            setOriginTitle(context.getResources().getString(R.string.choose_origin_default));
            presenter.setLastOrigin(null);
            logger.debug("Clean last origin");
        }
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
        IMapPlace lastDestination = presenter.getLastDestination();
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
        logger.debug("BuildRouteFragment swipeOriginAndDestination");
        IMapPlace lastOrigin = presenter.getLastOrigin();
        IMapPlace lastDestination = presenter.getLastDestination();
        setOriginPlace(lastDestination);
        setDestinationPlace(lastOrigin);
    }

    @Override
    public void setOnlyOrigin(@NonNull IMapPlace origin) {
        logger.debug("BuildRouteFragment setOnlyOrigin");
        if (!placesAreTheSame(origin, presenter.getLastDestination())) {
            presenter.setLastOrigin(origin);
        }
    }

    @Override
    public void setOnlyDestination(@NonNull IMapPlace destination) {
        logger.debug("BuildRouteFragment setOnlyDestination");
        if (!placesAreTheSame(destination, presenter.getLastOrigin())) {
            presenter.setLastDestination(destination);
        }
    }

    private void setDestinationPlace(@Nullable IMapPlace place) {
        if (place != null
                && (presenter.getLastDestination() == null
                || !placesAreTheSame(place, presenter.getLastOrigin()))) {
            setDestinationTitle(place.getTitle());
            presenter.setLastDestination(place);
            logger.debug("Set destination as: " + place);
        } else {
            setDestinationTitle(context.getResources().getString(R.string.choose_destination_default));
            presenter.setLastDestination(null);
            logger.debug("Clean last destination");
        }
    }

    private boolean placesAreTheSame(IMapPlace place, IMapPlace comparing) {
        return place != null && comparing != null
                && place.getLongitude() == comparing.getLongitude()
                && place.getLatitude() == comparing.getLatitude();
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
        logger.debug("BuildRouteFragment setNewPickedPlace");
        if (presenter.getLastOrigin() == null && !placesAreTheSame(mapPlace, presenter.getLastDestination())) {
            setOriginPlace(mapPlace);
            openShowRouteFragmentIfRequied();
        } else if (presenter.getLastDestination() == null && !placesAreTheSame(mapPlace, presenter.getLastOrigin())) {
            setDestinationPlace(mapPlace);
            openShowRouteFragmentIfRequied();
        } else {
            logger.error("Can't use picked new place");
        }
    }

    @Override
    public void deleteHistoryPlace(int position, @NonNull IMapPlace mapPlace) {
        presenter.removeHistoryPlace(mapPlace);
        adapter.removePlace(position, mapPlace);
    }

    @Override
    public void markAsFavouritePlace(@NonNull IMapPlace mapPlace) {
        setFavouriteState(mapPlace, true);
    }

    private void setFavouriteState(@NonNull IMapPlace mapPlace, boolean favourite) {
        List<IMapPlace> places = presenter.getHistoryPlaces();
        if (places != null && !places.isEmpty()) {
            int position = getPosition(places, mapPlace);

            mapPlace.setFavourite(favourite);
            places.set(position, mapPlace);

            presenter.setHistoryPlaces(places);
            adapter.updatePlace(mapPlace);
        }
    }

    @Override
    public void setNewFoundPlace(@NonNull IMapPlace mapPlace) {
        presenter.addNewHistoryPlace(mapPlace);
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
        if (presenter.getLastOrigin() != null && presenter.getLastDestination() != null) {
            IRoute route = buildNewRoute();
            presenter.setLastRoute(route);
            presenter.setLastOrigin(null);
            presenter.setLastDestination(null);
            fragmentController.openFragment(new ShowRouteFragment());
        }
    }

    @NonNull
    private IRoute buildNewRoute() {
        IMapPlace origin = presenter.getLastOrigin();
        IMapPlace destination = presenter.getLastDestination();
        return new Route("route_id", origin, destination, "Route", System.currentTimeMillis());
    }
}
