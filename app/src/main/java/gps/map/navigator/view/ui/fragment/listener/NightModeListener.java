package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.view.interfaces.IViewInteraction;

public class NightModeListener implements CompoundButton.OnCheckedChangeListener {

    private IViewInteraction viewInteraction;

    public NightModeListener(IViewInteraction viewInteraction) {
        this.viewInteraction = viewInteraction;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (viewInteraction != null) {
            viewInteraction.enableNightMode(isChecked);
        }
        Logger.debug("Night mode is active: " + isChecked);
    }
}
