package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.view.interfaces.IViewInteraction;

public class SatelliteModeListener implements CompoundButton.OnCheckedChangeListener {

    private IViewInteraction viewInteraction;

    public SatelliteModeListener(IViewInteraction viewInteraction) {
        this.viewInteraction = viewInteraction;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (viewInteraction != null) {
            viewInteraction.enableSatelliteMode(isChecked);
        }
        Logger.debug("Satellite mode is active: " + isChecked);
    }
}
