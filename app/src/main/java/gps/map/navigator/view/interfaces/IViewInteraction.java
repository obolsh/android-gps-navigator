package gps.map.navigator.view.interfaces;

import android.view.View;

import gps.map.navigator.model.interfaces.IRoute;

public interface IViewInteraction {

    void showMeOnMap(View view);

    void buildRoute();

    void startNavigate(IRoute route);

    void findPlaceAndShow();

    void findPlace();

    void enableTrafficMode(boolean enable);

    void enableNightMode(boolean enable);

    void enableSatelliteMode(boolean enable);

    boolean hasTrafficMode();

    boolean hasNightMode();

    boolean hasSatelliteMode();

    void backToMainScreen();

    void backToBuildRouteScreen();
}
