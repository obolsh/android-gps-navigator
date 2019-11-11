package gps.map.navigator.view.viewmodel.recyclerview.listener;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

public class DeleteListener implements View.OnClickListener {
    @Nullable
    private RecyclerView.ViewHolder viewHolder;
    @Nullable
    private IMapPlace mapPlace;
    @Nullable
    private IPlacePickerCallback placePickerCallback;

    public DeleteListener(@Nullable RecyclerView.ViewHolder viewHolder,
                          @Nullable IMapPlace mapPlace,
                          @Nullable IPlacePickerCallback placePickerCallback) {
        this.viewHolder = viewHolder;
        this.mapPlace = mapPlace;
        this.placePickerCallback = placePickerCallback;
    }

    @Override
    public void onClick(View v) {
        if (viewHolder != null && mapPlace != null && placePickerCallback != null) {
            placePickerCallback.deleteHistoryPlace(viewHolder.getAdapterPosition(), mapPlace);
        }
    }
}
