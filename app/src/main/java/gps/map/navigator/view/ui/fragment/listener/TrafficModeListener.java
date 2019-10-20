package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.view.interfaces.IViewInteraction;

public class TrafficModeListener implements CompoundButton.OnCheckedChangeListener {

    private IViewInteraction viewInteraction;

    public TrafficModeListener(IViewInteraction viewInteraction) {
        this.viewInteraction = viewInteraction;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (viewInteraction != null) {
            viewInteraction.enableTrafficMode(isChecked);
        }
        Logger.debug("Traffic mode is active: " + isChecked);
    }
}
