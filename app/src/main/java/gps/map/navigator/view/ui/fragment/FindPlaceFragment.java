package gps.map.navigator.view.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
import gps.map.navigator.model.interfaces.Cache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceHistoryListener;
import gps.map.navigator.view.ui.fragment.controller.IFragmentController;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;

@Singleton
public class FindPlaceFragment extends AbstractNaviFragment implements IPlacePickerCallback, ICachedPlaceCallback {

    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;
    @Inject
    Cache cache;
    @Inject
    IFragmentController<Fragment> fragmentController;
    @Inject
    IPlaceHistoryListener buildRouteCallback;
    @Inject
    AbstractAdapter adapter;
    @Inject
    SearchView.OnQueryTextListener searchListener;
    @Inject
    @Named(Constants.BackPressListener)
    View.OnClickListener backPressListener;
    @Inject
    Activity activity;
    private PlaceProxyListener listener;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.find_place_fragment, container, false);
        addPlacesToRecyclerView(root);
        setupSearchView(root);
        setupToolbarNavigation(root);
        presenter.buildRoute(buildRouteCallback);
        return root;
    }

    private void addPlacesToRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.history_places_container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);
    }

    private LinearLayoutManager getLayoutManager() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        return manager;
    }

    private void setupSearchView(View root) {
        searchView = root.findViewById(R.id.search_view_box);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(searchListener);
    }

    private void setupToolbarNavigation(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(backPressListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndFab();
        openKeyboard();
//        if (listener != null) {
//            presenter.findPlace(new FindPlaceCallback(listener));
//        }
    }

    private void hideBottomBarAndFab() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(false);
        decorController.setShowMeOnMapFabVisibility(false);
    }

    private void openKeyboard() {
        searchView.requestFocus();
        searchView.setIconified(false);
    }

    @Override
    public String getFragmentTag() {
        return FindPlaceFragment.class.getName();
    }

    public void setTask(PlaceProxyListener listener) {
        this.listener = listener;
    }

    @Override
    public void setNewPickedPlace(IMapPlace mapPlace) {
        if (listener != null) {
            listener.onPlaceLocated(mapPlace);
            activity.onBackPressed();
        } else {
            cache.setLastPlace(mapPlace);
            fragmentController.removeFromBackStack(this);
            fragmentController.openFragment(new ShowPlaceFragment());
        }
    }

    @Override
    public void deleteHistoryPlace(int position, IMapPlace mapPlace) {
        cache.removeHistoryPlace(mapPlace);
        adapter.removePlace(position, mapPlace);
    }

    @Override
    public void markAsFavouritePlace(IMapPlace mapPlace) {
        setFavouriteState(mapPlace, true);
    }

    private void setFavouriteState(IMapPlace mapPlace, boolean favourite) {
        List<IMapPlace> places = cache.getHistoryPlaces();
        int position = getPosition(places, mapPlace);

        mapPlace.setFavourite(favourite);
        places.set(position, mapPlace);

        cache.setHistoryPlaces(places);
        adapter.updatePlace(mapPlace);
    }

    @Override
    public void markAdNotFavouritePlace(IMapPlace mapPlace) {
        setFavouriteState(mapPlace, false);
    }

    @Override
    public void setNewFoundPlace(IMapPlace mapPlace) {
        cache.addNewHistoryPlace(mapPlace);
    }

    @Override
    public void setHistoryPlaces(List<IMapPlace> placeList) {
        adapter.setInitialPlacesList(placeList);
    }

    private int getPosition(List<IMapPlace> places, IMapPlace item) {
        for (int i = 0; i < places.size(); i++) {
            if (item.getId().equals(places.get(i).getId())) {
                return i;
            }
        }
        return 0;
    }
}
