package gps.map.navigator.view.viewmodel.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gps.map.navigator.R;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.BuildRouteFragment;

public class MapPlaceAdapter extends RecyclerView.Adapter<MapPlaceViewHolder> {

    private List<IMapPlace> places;
    private BuildRouteFragment fragment;

    public MapPlaceAdapter(BuildRouteFragment fragment) {
        this.fragment = fragment;
    }

    public void setPlaces(List<IMapPlace> places) {
        this.places = places;
        notifyDataSetChanged();
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
}
