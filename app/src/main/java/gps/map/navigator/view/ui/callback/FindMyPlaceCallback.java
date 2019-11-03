package gps.map.navigator.view.ui.callback;

import android.view.View;

import javax.inject.Inject;

import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.model.interfaces.Invalidator;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.viewmodel.callback.ShowMeOnMapCallback;

public class FindMyPlaceCallback implements View.OnClickListener, Invalidator {

    @Inject
    Presenter presenter;

    @Inject
    FindMyPlaceCallback() {
    }

    @Override
    public void onClick(View v) {
        Logger.debug("Find me on map triggered");
        presenter.showMeOnMap(new ShowMeOnMapCallback());
    }

    @Override
    public void invalidate() {
        presenter = null;
    }
}
