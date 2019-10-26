package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;


import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.presenter.interfaces.Presenter;

public class NightModeListener implements CompoundButton.OnCheckedChangeListener {

    private Presenter presenterStrategy;

    public NightModeListener(Presenter presenter) {
        this.presenterStrategy = presenter;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (presenterStrategy != null) {
            presenterStrategy.enableNightMode(isChecked);
        }
        Logger.debug("Night mode is active: " + isChecked);
    }
}
