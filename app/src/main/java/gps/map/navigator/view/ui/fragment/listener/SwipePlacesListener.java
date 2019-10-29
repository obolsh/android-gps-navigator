package gps.map.navigator.view.ui.fragment.listener;

import android.view.View;

import gps.map.navigator.view.ui.fragment.BuildRouteFragment;

public class SwipePlacesListener implements View.OnClickListener {
    private BuildRouteFragment fragment;

    public SwipePlacesListener(BuildRouteFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        if (fragment != null) {
            fragment.swipeOriginAndDestination();
        }
        fragment = null;
    }
}
