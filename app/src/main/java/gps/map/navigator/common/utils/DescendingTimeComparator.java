package gps.map.navigator.common.utils;

import androidx.annotation.NonNull;

import java.util.Comparator;

import gps.map.navigator.model.interfaces.IMapPlace;

/**
 * Sort list descending by last used time.
 */
public class DescendingTimeComparator implements Comparator<IMapPlace> {
    @Override
    public int compare(@NonNull IMapPlace one, @NonNull IMapPlace two) {
        Long oneTime = one.getLastUsedTime();
        Long twoTime = two.getLastUsedTime();
        return Integer.compare(twoTime.compareTo(oneTime), 0);
    }
}
