package gps.map.navigator.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.R;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.ui.fragment.listener.ChoosePlaceCallback;
import gps.map.navigator.view.ui.fragment.listener.SwipePlacesListener;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.BuildRouteCallback;
import gps.map.navigator.view.viewmodel.recyclerview.MapPlaceAdapter;

public class BuildRouteFragment extends AbstractNaviFragment {
    @Inject
    Presenter presenter;
    @Inject
    Cache cache;
    @Inject
    DecorController decorController;
    @Inject
    IFragmentController<Fragment> fragmentController;

    private MapPlaceAdapter adapter;
    private IMapPlace originPlace;
    private IMapPlace destinationPlace;
    private TextView originTitle;
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
        presenter.buildRoute(new BuildRouteCallback(this));
        setupOrigin(view);
        setupDestination(view);
        setupSwipeOriginAndDestination(view);
        return view;
    }

    private void addHistoryPlacesToRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.history_places_container);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MapPlaceAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupOrigin(View view) {
        originTitle = view.findViewById(R.id.origin_title);
        originTitle.setOnClickListener(new ChoosePlaceCallback(fragmentController));
        pickBestLastOrigin();
    }

    private void pickBestLastOrigin() {
        IMapPlace lastOrigin = cache.getLastOrigin();
        if (lastOrigin != null) {
            setOriginPlace(lastOrigin);
        } else {
            setOriginPlace(cache.getMyLocation());
        }
    }

    private void setOriginPlace(IMapPlace place) {
        if (place != null) {
            setOriginTitle(place.getTitle());
            originPlace = place;
        } else {
            setOriginTitle(getResources().getString(R.string.choose_origin_default));
            originPlace = null;
        }
        cache.setLastOrigin(originPlace);
    }

    private void setOriginTitle(String title) {
        if (originTitle != null) {
            originTitle.setText(title);
        }
    }

    private void setupDestination(View view) {
        destinationTitle = view.findViewById(R.id.destination_title);
        destinationTitle.setOnClickListener(new ChoosePlaceCallback(fragmentController));
        pickBestDestination();
    }

    private void pickBestDestination() {
        IMapPlace mapPlace = cache.getLastDestination();
        setDestinationPlace(mapPlace);
    }

    private void setupSwipeOriginAndDestination(View view) {
        ImageView button = view.findViewById(R.id.swipe_origin_and_destination_button);
        button.setOnClickListener(new SwipePlacesListener(this));
    }

    public void swipeOriginAndDestination() {
        IMapPlace lastOrigin = originPlace;
        IMapPlace lastDestination = destinationPlace;
        setOriginPlace(lastDestination);
        setDestinationPlace(lastOrigin);
    }

    private void setDestinationPlace(IMapPlace place) {
        if (place != null) {
            setDestinationTitle(place.getTitle());
            destinationPlace = place;
        } else {
            setDestinationTitle(getResources().getString(R.string.choose_destination_default));
            destinationPlace = null;
        }
        cache.setLastDestination(destinationPlace);
    }

    private void setDestinationTitle(String title) {
        if (destinationTitle != null) {
            destinationTitle.setText(title);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndMoveFabToRight();
    }

    private void hideBottomBarAndMoveFabToRight() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(false);
        decorController.setShowMeOnMapFabVisibility(false);
    }

    @Override
    public String getFragmentTag() {
        return BuildRouteFragment.class.getName();
    }

    public void setHistoryPlaces(List<IMapPlace> placeList) {
        if (adapter != null) {
            adapter.setPlaces(placeList);
        }
    }

    public void setNewPickedPlace(IMapPlace mapPlace) {
        if (originPlace == null && destinationPlace != null) {
            setOriginPlace(mapPlace);
        } else if (destinationPlace == null && originPlace != null) {
            setDestinationPlace(mapPlace);
        } else {
            Logger.error("Can't use picked new place");
        }
        fragmentController.openFragment(new ShowRouteFragment());
    }
}
