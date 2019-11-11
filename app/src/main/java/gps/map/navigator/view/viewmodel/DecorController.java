package gps.map.navigator.view.viewmodel;

public interface DecorController {
    /**
     * Change visibility state for Bottom Bar.
     *
     * @param visible - true for visible, false for invisible.
     */
    void setBottomBarVisibility(boolean visible);

    /**
     * Set floating action button alignment.
     *
     * @param mode - alignment.
     */
    void setFabAlignmentMode(int mode);

    /**
     * Set floating action button visibility.
     *
     * @param visible - true for visible, false for invisible.
     */
    void setFabVisibility(boolean visible);

    /**
     * Set show me on map floating action button visibility.
     *
     * @param visible - true for visible, false for invisible.
     */
    void setShowMeOnMapFabVisibility(boolean visible);
}
