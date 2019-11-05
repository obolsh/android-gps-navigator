package gps.map.navigator.view.ui.fragment.listener;

import android.view.View;

import javax.inject.Inject;

public class SwipePlacesListener implements View.OnClickListener {
    @Inject
    ISwipeRoute fragment;

    @Inject
    SwipePlacesListener() {
    }

    @Override
    public void onClick(View v) {
        if (fragment != null) {
            fragment.swipeOriginAndDestination();
        }
    }
}
