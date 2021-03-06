package gps.map.navigator.view.ui.fragment.listener;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.PlaceProxyListener;
import gps.map.navigator.view.viewmodel.recyclerview.AbstractAdapter;

public class ShowSinglePlaceListener implements PlaceProxyListener {
    @Inject
    AbstractAdapter adapter;

    @Inject
    ShowSinglePlaceListener() {
    }

    @Override
    public void onPlaceLocated(@NonNull IMapPlace mapPlace) {
        adapter.showSinglePlace(mapPlace);
    }

    @Override
    public void onPlacesLocated(@NonNull List<IMapPlace> mapPlaces) {
        adapter.showFoundedPlacesList(mapPlaces);
    }
}
