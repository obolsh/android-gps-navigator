package gps.map.navigator.view.ui.fragment.controller;

import androidx.annotation.NonNull;

public interface IFragment<T> {
    /**
     * Get fragment instance object.
     *
     * @return - instance object.
     */
    @NonNull
    T getInstance();

    /**
     * Get fragment tag.
     *
     * @return - gragment tag.
     */
    @NonNull
    String getFragmentTag();
}
