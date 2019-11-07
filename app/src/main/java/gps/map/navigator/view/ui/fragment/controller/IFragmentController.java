package gps.map.navigator.view.ui.fragment.controller;

public interface IFragmentController<T> {
    /**
     * open fragment.
     *
     * @param fragment - fragment to be opened.
     */
    void openFragment(IFragment<T> fragment);

    /**
     * Check if fragment is active now.
     *
     * @param fragmentClass - fragment's class.
     * @return true if active, false otherwise.
     */
    boolean thisFragmentIsActive(Class fragmentClass);

    /**
     * Remove fragment from backstack.
     *
     * @param fragment - fragment to be removed.
     */
    void removeFromBackStack(IFragment<T> fragment);
}
