package gps.map.navigator.view.ui.fragment.listener;


import android.widget.SearchView;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.callback.FindPlaceCallback;
import gps.map.navigator.view.viewmodel.recyclerview.MapPlaceAdapter;

public class SearchTextListener implements SearchView.OnQueryTextListener {

    private MapPlaceAdapter adapter;
    private Presenter presenter;

    public SearchTextListener(MapPlaceAdapter adapter, Presenter presenter) {
        this.adapter = adapter;
        this.presenter = presenter;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.findPlace(new FindPlaceCallback(new PlaceProxyListener() {
            @Override
            public void onPlaceLocated(IMapPlace mapPlace) {
                adapter.showSinglePlace(mapPlace);
            }
        }));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }
}
