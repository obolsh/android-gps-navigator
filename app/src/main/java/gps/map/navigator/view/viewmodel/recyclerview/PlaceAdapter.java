package gps.map.navigator.view.viewmodel.recyclerview;

import android.widget.Filterable;

import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

public interface PlaceAdapter extends Filterable {

    List<IMapPlace> getOriginalPlacesList();

    void setPlacesList(List<IMapPlace> placeList);

    void showSinglePlace(IMapPlace mapPlace);

    void removePlace(int position, IMapPlace place);

    void updatePlace(IMapPlace update);

    void setPlaces(List<IMapPlace> places);
}
