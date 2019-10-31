package gps.map.navigator.view.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.listener.ICachedPlaceCallback;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;
import gps.map.navigator.view.viewmodel.DecorController;
import gps.map.navigator.view.viewmodel.callback.BuildRouteCallback;
import gps.map.navigator.view.viewmodel.recyclerview.MapPlaceAdapter;

public class FindPlaceFragment extends AbstractNaviFragment implements IPlacePickerCallback, ICachedPlaceCallback {

    @Inject
    DecorController decorController;
    @Inject
    Presenter presenter;
    private PlaceProxyListener listener;
    private MapPlaceAdapter adapter;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.find_place_fragment, container, false);
        addPlacesToRecyclerView(root);
        setupSearchView(root);
        setupToolbarNavigation(root);
        presenter.buildRoute(new BuildRouteCallback(this));
        return root;
    }

    private void addPlacesToRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.history_places_container);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MapPlaceAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView(View root) {
        searchView = root.findViewById(R.id.search_view_box);
        searchView.setIconifiedByDefault(false);
    }

    private void setupToolbarNavigation(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
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
            getActivity().onBackPressed();
        }
    }

    @Override
    public void setHistoryPlaces(List<IMapPlace> placeList) {
        if (adapter != null) {
            adapter.setPlaces(placeList);
        }
    }
}
