package gps.map.navigator.common.utils;

import androidx.annotation.Nullable;

import java.util.Comparator;

import gps.map.navigator.model.interfaces.IMapPlace;

/**
 * Sort list descending by last used time.
 */
public class DescendingTimeComparator implements Comparator<IMapPlace> {
    @Override
    public int compare(@Nullable IMapPlace one, @Nullable IMapPlace two) {
        if (one != null && two != null) {
            Long oneTime = one.getLastUsedTime();
            Long twoTime = two.getLastUsedTime();
            return Integer.compare(twoTime.compareTo(oneTime), 0);
        } else {
            return 0;
        }
    }
}
