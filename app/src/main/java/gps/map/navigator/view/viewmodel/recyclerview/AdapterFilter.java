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
        if (adapter != null && results != null && results.values instanceof List) {
            List<IMapPlace> list = (List<IMapPlace>) results.values;
            if (!list.isEmpty()) {
                adapter.changePlacesList(list);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        Result results = new Result();
        List<IMapPlace> originals;
        if (adapter != null) {
            originals = adapter.getOriginalPlacesList();
            if (constraint.length() != 0) {
                results.values = getFilteredResults(originals, constraint.toString().toLowerCase());
            } else {
                results.values = originals;
            }
        }
        return results;
    }

    @NonNull
    private List<IMapPlace> getFilteredResults(@Nullable List<IMapPlace> originalPlacesList, @NonNull String constraint) {
        List<IMapPlace> results = new ArrayList<>();
        if (originalPlacesList != null && !originalPlacesList.isEmpty()) {
            for (IMapPlace item : originalPlacesList) {
                if (itemMatch(item, constraint)) {
                    results.add(item);
                }
            }
        }
        return results;
    }

    private boolean itemMatch(IMapPlace item, String constraint) {
        return item != null && constraint != null
                && (item.getTitle().toLowerCase().contains(constraint)
                || item.getAddress().toLowerCase().contains(constraint));
    }

    static class Result extends FilterResults {

    }
}
