package gps.map.navigator.view.ui.fragment.listener;

import android.view.View;

public class SwipePlacesListener implements View.OnClickListener {
    private ISwipeRoute fragment;

    public SwipePlacesListener(ISwipeRoute fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        if (fragment != null) {
            fragment.swipeOriginAndDestination();
        }
    }
}
