package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.presenter.interfaces.Presenter;

public class TrafficModeListener implements CompoundButton.OnCheckedChangeListener {

    private Presenter presenterStrategy;

    public TrafficModeListener(Presenter presenter) {
        this.presenterStrategy = presenter;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (presenterStrategy != null) {
            presenterStrategy.enableTraffic(isChecked);
        }
        Logger.debug("Traffic mode is active: " + isChecked);
    }
}
