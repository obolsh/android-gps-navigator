package gps.map.navigator.view.viewmodel.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import gps.map.navigator.R;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.common.utils.DescendingTimeComparator;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

public class MapPlaceAdapter extends AbstractAdapter {

    private List<IMapPlace> places;
    private List<IMapPlace> originalPlacesList;
    @Inject
    IPlacePickerCallback fragment;
    @Inject
    Logger logger;

    @Inject
    MapPlaceAdapter() {
    }

    @NonNull
    @Override
    public MapPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.map_place_view, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.debug("Picked new place item");
            }
        });
        return new MapPlaceViewHolder(itemView, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull MapPlaceViewHolder holder, int position) {
        if (places != null) {
            IMapPlace place = places.get(position);
            if (place != null) {
                holder.setMapPlace(place);
            }
        }
    }

    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
    }

    @Override
    public List<IMapPlace> getOriginalPlacesList() {
        return originalPlacesList;
    }

    @Override
    public void setPlacesList(List<IMapPlace> placeList) {
        this.places = placeList;
    }

    @Override
    public Filter getFilter() {
        return new AdapterFilter(this);
    }

    @Override
    public void showSinglePlace(IMapPlace mapPlace) {
        if (places != null) {
            places.clear();
        } else {
            places = new ArrayList<>();
        }
        places.add(mapPlace);
        if (originalPlacesList == null) {
            originalPlacesList = new ArrayList<>();
        }
        originalPlacesList.add(mapPlace);
        sortByLastUsedTime(originalPlacesList);
        notifyDataSetChanged();
        fragment.setNewFoundPlace(mapPlace);
    }

    @Override
    public void removePlace(int position, IMapPlace place) {
        places.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, places.size());
        originalPlacesList.remove(place);
    }

    @Override
    public void updatePlace(IMapPlace update) {
        int position = getPosition(places, update);
        places.set(position, update);
        int originalPosition = getPosition(originalPlacesList, update);
        originalPlacesList.set(originalPosition, update);
        notifyItemChanged(position);
    }

    @Override
    public void setPlaces(List<IMapPlace> places) {
        this.originalPlacesList = places;
        sortByLastUsedTime(originalPlacesList);
        this.places = originalPlacesList;
        notifyItemInserted(places.size() - 1);
    }

    private int getPosition(List<IMapPlace> places, IMapPlace item) {
        for (int i = 0; i < places.size(); i++) {
            if (item.getId().equals(places.get(i).getId())) {
                return i;
            }
        }
        return 0;
    }

    private void sortByLastUsedTime(List<IMapPlace> places) {
        Collections.sort(places, new DescendingTimeComparator());
    }
}
