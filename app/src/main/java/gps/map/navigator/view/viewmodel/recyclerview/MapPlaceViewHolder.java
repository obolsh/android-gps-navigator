package gps.map.navigator.view.viewmodel.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;
import gps.map.navigator.view.viewmodel.recyclerview.listener.DeleteListener;
import gps.map.navigator.view.viewmodel.recyclerview.listener.FavouriteListener;
import gps.map.navigator.view.viewmodel.recyclerview.listener.PickedListener;

class MapPlaceViewHolder extends RecyclerView.ViewHolder {

    private ImageView favouriteImage;
    private TextView titleView;
    private TextView addressView;
    private IMapPlace mapPlace;
    private IPlacePickerCallback fragment;
    private ImageView deleteButton;

    MapPlaceViewHolder(@NonNull View itemView, @NonNull IPlacePickerCallback fragment) {
        super(itemView);
        this.fragment = fragment;
        favouriteImage = itemView.findViewById(R.id.favourite_map_place);
        titleView = itemView.findViewById(R.id.map_place_title);
        addressView = itemView.findViewById(R.id.map_place_address);
        deleteButton = itemView.findViewById(R.id.delete_map_place);
    }

    void setMapPlace(@NonNull IMapPlace mapPlace) {
        this.mapPlace = mapPlace;
        setPlaceFavourite(mapPlace.isFavourite());
        setTitle(mapPlace.getTitle());
        setAddress(mapPlace.getAddress());
        addListeners();
    }

    private void addListeners() {
        favouriteImage.setOnClickListener(new FavouriteListener(mapPlace, fragment));
        deleteButton.setOnClickListener(new DeleteListener(this, mapPlace, fragment));
        itemView.setOnClickListener(new PickedListener(mapPlace, fragment));
    }

    private void setPlaceFavourite(boolean favourite) {
        if (favourite) {
            favouriteImage.setImageResource(android.R.drawable.star_big_on);
        } else {
            favouriteImage.setImageResource(android.R.drawable.star_big_off);
        }
    }

    private void setTitle(String placeTitle) {
        titleView.setText(placeTitle);
    }

    private void setAddress(String address) {
        addressView.setText(address);
    }
}
