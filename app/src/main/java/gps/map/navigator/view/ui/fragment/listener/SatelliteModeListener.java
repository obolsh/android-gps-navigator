package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.presenter.interfaces.Presenter;

public class SatelliteModeListener implements CompoundButton.OnCheckedChangeListener {

    private Presenter presenterStrategy;

    public SatelliteModeListener(Presenter presenter) {
        this.presenterStrategy = presenter;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (presenterStrategy != null) {
            presenterStrategy.enableSatelliteMode(isChecked);
        }
        Logger.debug("Satellite mode is active: " + isChecked);
    }
}
