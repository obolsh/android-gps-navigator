package gps.map.navigator.view.ui.fragment.controller;

public interface IFragment<T> {
    /**
     * Get fragment instance object.
     *
     * @return - instance object.
     */
    T getInstance();

    /**
     * Get fragment tag.
     *
     * @return - gragment tag.
     */
    String getFragmentTag();
}
