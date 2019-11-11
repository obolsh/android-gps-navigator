package gps.map.navigator.view.ui.fragment.listener;

import android.widget.CompoundButton;

import javax.inject.Inject;

import gps.map.navigator.presenter.interfaces.Presenter;

public class SatelliteModeListener implements CompoundButton.OnCheckedChangeListener {

    @Inject
    Presenter presenter;

    @Inject
    SatelliteModeListener() {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenter.enableSatelliteMode(isChecked);
    }
}
