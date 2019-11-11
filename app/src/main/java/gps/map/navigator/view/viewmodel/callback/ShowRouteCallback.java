package gps.map.navigator.view.viewmodel.callback;

import androidx.annotation.NonNull;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IRouteReadyListener;

public class ShowRouteCallback implements IRouteReadyListener {
    @Override
    public void onRouteReady(@NonNull IRoute route) {

    }

    @Override
    public void onRouteFailed(@NonNull Exception reason) {

    }
}
