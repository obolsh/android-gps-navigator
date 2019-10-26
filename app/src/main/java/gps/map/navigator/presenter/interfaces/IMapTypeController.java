package gps.map.navigator.presenter.interfaces;

public interface IMapTypeController {

    void enableTraffic(boolean enable);

    void enableSatellite(boolean enable);

    void enableNightMode(boolean enable);

    boolean hasTrafficMode();

    boolean hasSatelliteMode();

    boolean hasNightMode();
}
