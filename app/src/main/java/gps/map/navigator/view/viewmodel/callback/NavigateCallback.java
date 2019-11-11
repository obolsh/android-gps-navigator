package gps.map.navigator.view.viewmodel.callback;

import androidx.annotation.NonNull;

import gps.map.navigator.model.interfaces.IRoute;
import gps.map.navigator.view.interfaces.IRouteListener;

public class NavigateCallback implements IRouteListener {
    @Override
    public void onRouteStarted(@NonNull IRoute route) {

    }

    @Override
    public void onRouteStopped(@NonNull IRoute route) {

    }

    @Override
    public void onRouteError(@NonNull IRoute route, @NonNull Exception reason) {

    }
}
