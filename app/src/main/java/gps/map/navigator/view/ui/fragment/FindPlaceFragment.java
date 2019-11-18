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
    @Nullable
    private PlaceProxyListener listener;
    @Nullable
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

    private void addPlacesToRecyclerView(@NonNull View root) {
        RecyclerView recyclerView = root.findViewById(R.id.history_places_container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private LinearLayoutManager getLayoutManager() {
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(RecyclerView.VERTICAL);
        return manager;
    }

    private void setupSearchView(@NonNull View root) {
        searchView = root.findViewById(R.id.search_view_box);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(searchListener);
    }

    private void setupToolbarNavigation(@NonNull View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(backPressListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBottomBarAndFab();
//        openKeyboard();
    }

    private void hideBottomBarAndFab() {
        decorController.setBottomBarVisibility(false);
        decorController.setFabVisibility(false);
        decorController.setShowMeOnMapFabVisibility(false);
    }

    private void openKeyboard() {
        if (searchView != null) {
            searchView.requestFocus();
            searchView.setIconified(false);
        }
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return FindPlaceFragment.class.getName();
    }

    public void setTask(@Nullable PlaceProxyListener listener) {
        this.listener = listener;
    }

    @Override
    public void setNewPickedPlace(@NonNull IMapPlace mapPlace) {
        if (listener != null) {
            listener.onPlaceLocated(mapPlace);
            activity.onBackPressed();
        } else {
            presenter.setLastPlace(mapPlace);
            fragmentController.removeFromBackStack(this);
            fragmentController.openFragment(new ShowPlaceFragment());
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
    public void markAdNotFavouritePlace(@NonNull IMapPlace mapPlace) {
        setFavouriteState(mapPlace, false);
    }

    @Override
    public void setNewFoundPlace(@NonNull IMapPlace mapPlace) {
        presenter.addNewHistoryPlace(mapPlace);
    }

    @Override
    public void setHistoryPlaces(@NonNull List<IMapPlace> placeList) {
        adapter.setInitialPlacesList(placeList);
    }

    private int getPosition(@NonNull List<IMapPlace> places, @NonNull IMapPlace item) {
        for (int i = 0; i < places.size(); i++) {
            if (item.getId().equals(places.get(i).getId())) {
                return i;
            }
        }
        return 0;
    }
}
