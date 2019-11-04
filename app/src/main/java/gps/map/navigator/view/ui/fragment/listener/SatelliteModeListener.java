package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;

import javax.inject.Inject;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.presenter.interfaces.Presenter;

public class SatelliteModeListener implements CompoundButton.OnCheckedChangeListener {

    @Inject
    Presenter presenterStrategy;

    @Inject
    SatelliteModeListener() {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenterStrategy.enableSatelliteMode(isChecked);
        Logger.debug("Satellite mode is active: " + isChecked);
    }
}
