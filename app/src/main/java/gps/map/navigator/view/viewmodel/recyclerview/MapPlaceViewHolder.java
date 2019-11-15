package gps.map.navigator.view.viewmodel.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import gps.map.navigator.R;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;
import gps.map.navigator.view.viewmodel.recyclerview.listener.DeleteListener;
import gps.map.navigator.view.viewmodel.recyclerview.listener.FavouriteListener;
import gps.map.navigator.view.viewmodel.recyclerview.listener.PickedListener;

class MapPlaceViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    private ImageView favouriteImage;
    @Nullable
    private TextView titleView;
    @Nullable
    private TextView addressView;
    @Nullable
    private IMapPlace mapPlace;
    @Nullable
    private IPlacePickerCallback fragment;
    @Nullable
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
        if (favouriteImage != null) {
            favouriteImage.setOnClickListener(new FavouriteListener(mapPlace, fragment));
        }
        if (deleteButton != null) {
            deleteButton.setOnClickListener(new DeleteListener(this, mapPlace, fragment));
        }
        itemView.setOnClickListener(new PickedListener(mapPlace, fragment));
    }

    private void setPlaceFavourite(boolean favourite) {
        if (favouriteImage != null) {
            if (favourite) {
                favouriteImage.setImageResource(R.drawable.outline_star_black_24dp);
            } else {
                favouriteImage.setImageResource(R.drawable.outline_star_border_black_24dp);
            }
        }
    }

    private void setTitle(@Nullable String placeTitle) {
        if (titleView != null && placeTitle != null) {
            titleView.setText(placeTitle);
        }
    }

    private void setAddress(@Nullable String address) {
        if (addressView != null && address != null) {
            addressView.setText(address);
        }
    }
}
