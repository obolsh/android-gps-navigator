package gps.map.navigator.view.ui.fragment.controller;

import androidx.annotation.NonNull;

public interface IFragmentController<T> {
    /**
     * open fragment.
     *
     * @param fragment - fragment to be opened.
     */
    void openFragment(@NonNull IFragment<T> fragment);

    /**
     * Check if fragment is active now.
     *
     * @param fragmentClass - fragment's class.
     * @return true if active, false otherwise.
     */
    boolean thisFragmentIsActive(@NonNull Class fragmentClass);

    /**
     * Remove fragment from backstack.
     *
     * @param fragment - fragment to be removed.
     */
    void removeFromBackStack(@NonNull IFragment<T> fragment);
}
