package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;


import javax.inject.Inject;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.presenter.interfaces.Presenter;

public class NightModeListener implements CompoundButton.OnCheckedChangeListener {

    @Inject
    Presenter presenterStrategy;

    @Inject
    NightModeListener() {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (presenterStrategy != null) {
            presenterStrategy.enableNightMode(isChecked);
        }
        Logger.debug("Night mode is active: " + isChecked);
    }
}
