package gps.map.navigator.view.viewmodel.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.R;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

public class MapPlaceAdapter extends RecyclerView.Adapter<MapPlaceViewHolder> implements Filterable {

    private List<IMapPlace> places;
    private List<IMapPlace> originalPlacesList;
    private IPlacePickerCallback fragment;

    public MapPlaceAdapter(IPlacePickerCallback fragment) {
        this.fragment = fragment;
    }

    public void setPlaces(List<IMapPlace> places) {
        this.originalPlacesList = places;
        this.places = originalPlacesList;
        notifyItemInserted(places.size() - 1);
    }

    @NonNull
    @Override
    public MapPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.map_place_view, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debug("Picked new place item");
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

    private List<IMapPlace> getOriginalPlacesList() {
        return originalPlacesList;
    }

    private void setPlacesList(List<IMapPlace> placeList) {
        this.places = placeList;
    }

    @Override
    public Filter getFilter() {
        return new AdapterFiler(this);
    }

    public void showSinglePlace(IMapPlace mapPlace) {
        if (places != null) {
            places.clear();
        } else {
            places = new ArrayList<>();
        }
        places.add(mapPlace);
        notifyDataSetChanged();
    }

    private static class AdapterFiler extends Filter {

        private MapPlaceAdapter adapter;

        private AdapterFiler(MapPlaceAdapter adapter) {
            this.adapter = adapter;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.setPlacesList((List<IMapPlace>) results.values);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<IMapPlace> filteredResults;
            if (constraint.length() == 0) {
                filteredResults = adapter.getOriginalPlacesList();
            } else {
                filteredResults = getFilteredResults(adapter.getOriginalPlacesList(), constraint.toString().toLowerCase());
            }

            FilterResults results = new FilterResults();
            results.values = filteredResults;

            return results;
        }

        private List<IMapPlace> getFilteredResults(List<IMapPlace> originalPlacesList, String constraint) {
            List<IMapPlace> results = new ArrayList<>();
            String title;
            String adress;
            for (IMapPlace item : originalPlacesList) {
                title = item.getTitle().toLowerCase();
                adress = item.getAddress().toLowerCase();
                if (title.contains(constraint) || adress.contains(constraint)) {
                    results.add(item);
                }
            }
            return results;
        }
    }
}
