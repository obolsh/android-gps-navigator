package gps.map.navigator.view.viewmodel.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @Inject
    IPlacePickerCallback fragment;
    @Inject
    Logger logger;
    @Nullable
    private List<IMapPlace> places;
    @Nullable
    private List<IMapPlace> originalPlacesList;

    @Inject
    MapPlaceAdapter() {
    }

    @NonNull
    @Override
    public MapPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.map_place_view, parent, false);
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

    @Nullable
    @Override
    public List<IMapPlace> getOriginalPlacesList() {
        return originalPlacesList;
    }

    @Override
    public void changePlacesList(@NonNull List<IMapPlace> placeList) {
        this.places = placeList;
    }

    @Override
    public Filter getFilter() {
        return new AdapterFilter(this);
    }

    @Override
    public void showSinglePlace(@NonNull IMapPlace mapPlace) {
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
    public void removePlace(int position, @NonNull IMapPlace place) {
        if (places != null) {
            places.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, places.size());
        }
        if (originalPlacesList != null) {
            originalPlacesList.remove(place);
        }
    }

    @Override
    public void updatePlace(@NonNull IMapPlace update) {
        if (places != null && originalPlacesList != null) {
            if (places.isEmpty()) {
                places.add(update);
            } else {
                int position = getPosition(places, update);
                places.set(position, update);
                if (originalPlacesList.isEmpty()) {
                    originalPlacesList.add(update);
                } else {
                    int originalPosition = getPosition(originalPlacesList, update);
                    originalPlacesList.set(originalPosition, update);
                }
                notifyItemChanged(position);
            }
        }
    }

    @Override
    public void setInitialPlacesList(@NonNull List<IMapPlace> places) {
        this.originalPlacesList = places;
        sortByLastUsedTime(originalPlacesList);
        this.places = originalPlacesList;
        notifyItemInserted(places.size() - 1);
    }

    @Override
    public void showFoundedPlacesList(@NonNull List<IMapPlace> foundedPlaces) {
        if (places != null) {
            places.clear();
        } else {
            places = new ArrayList<>();
        }
        places.addAll(foundedPlaces);
        sortByLastUsedTime(places);
        notifyDataSetChanged();
    }

    private int getPosition(@NonNull List<IMapPlace> places, @NonNull IMapPlace item) {
        if (places.isEmpty()) {
            return 0;
        }
        for (int i = 0; i < places.size(); i++) {
            if (item.getId().equals(places.get(i).getId())) {
                return i;
            }
        }
        return 0;
    }

    private void sortByLastUsedTime(@NonNull List<IMapPlace> places) {
        if (!places.isEmpty()) {
            Collections.sort(places, new DescendingTimeComparator());
        }
    }
}
