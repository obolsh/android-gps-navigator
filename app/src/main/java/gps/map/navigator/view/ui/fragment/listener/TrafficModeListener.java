package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;

import javax.inject.Inject;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.presenter.interfaces.Presenter;

public class TrafficModeListener implements CompoundButton.OnCheckedChangeListener {

    @Inject
    Presenter presenterStrategy;
    @Inject
    Logger logger;

    @Inject
    TrafficModeListener() {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenterStrategy.enableTraffic(isChecked);
        logger.debug("Traffic mode is active: " + isChecked);
    }
}
