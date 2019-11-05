package gps.map.navigator.view.viewmodel.recyclerview.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.view.ui.fragment.listener.IPlacePickerCallback;

public class DeleteListener implements View.OnClickListener {

    private RecyclerView.ViewHolder viewHolder;
    private IMapPlace mapPlace;
    private IPlacePickerCallback placePickerCallback;

    public DeleteListener(RecyclerView.ViewHolder viewHolder, IMapPlace mapPlace, IPlacePickerCallback placePickerCallback) {
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
