package gps.map.navigator.view.viewmodel.recyclerview;

import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

public class AdapterFilter extends Filter {
    @Nullable
    private AbstractAdapter adapter;

    AdapterFilter(@Nullable AbstractAdapter adapter) {
        this.adapter = adapter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (adapter != null) {
            adapter.changePlacesList((List<IMapPlace>) results.values);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (adapter != null) {
            List<IMapPlace> filteredResults;
            if (constraint.length() == 0) {
                filteredResults = adapter.getOriginalPlacesList();
            } else {
                filteredResults = getFilteredResults(adapter.getOriginalPlacesList(), constraint.toString().toLowerCase());
            }

            results.values = filteredResults;
        }

        return results;
    }

    @NonNull
    private List<IMapPlace> getFilteredResults(@Nullable List<IMapPlace> originalPlacesList, @NonNull String constraint) {
        List<IMapPlace> results = new ArrayList<>();
        if (originalPlacesList != null) {
            String title;
            String adress;
            for (IMapPlace item : originalPlacesList) {
                title = item.getTitle().toLowerCase();
                adress = item.getAddress().toLowerCase();
                if (title.contains(constraint) || adress.contains(constraint)) {
                    results.add(item);
                }
            }
        }
        return results;
    }
}
