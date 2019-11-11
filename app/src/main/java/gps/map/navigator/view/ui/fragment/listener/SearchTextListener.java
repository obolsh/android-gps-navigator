package gps.map.navigator.view.ui.fragment.listener;

import android.widget.SearchView;

import javax.inject.Inject;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.interfaces.IPlaceListener;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;

public class SearchTextListener implements SearchView.OnQueryTextListener {

    @Inject
    AbstractAdapter adapter;
    @Inject
    Presenter presenter;
    @Inject
    IPlaceListener placeListener;

    @Inject
    SearchTextListener() {
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.findPlace(query, placeListener);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }
}
