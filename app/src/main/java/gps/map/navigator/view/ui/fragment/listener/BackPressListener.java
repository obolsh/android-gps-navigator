package gps.map.navigator.view.ui.fragment.listener;

import android.app.Activity;
import android.view.View;

import javax.inject.Inject;

public class BackPressListener implements View.OnClickListener {
    @Inject
    Activity activity;

    @Inject
    BackPressListener() {
    }

    @Override
    public void onClick(View v) {
        activity.onBackPressed();
    }
}
