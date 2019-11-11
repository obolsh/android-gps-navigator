package gps.map.navigator.presenter.interfaces;

public interface IMapTypeController {
    /**
     * show traffic.
     *
     * @param enable - true/false to show/hide.
     */
    void enableTraffic(boolean enable);

    /**
     * show satellite.
     *
     * @param enable - true/false to show/hide.
     */
    void enableSatellite(boolean enable);

    /**
     * show night.
     *
     * @param enable - true/false to show/hide.
     */
    void enableNightMode(boolean enable);

    /**
     * check if has traffic.
     *
     * @return - true if has, false otherwise.
     */
    boolean hasTrafficMode();

    /**
     * check if has satellite.
     *
     * @return - true if has, false otherwise.
     */
    boolean hasSatelliteMode();

    /**
     * check if has night.
     *
     * @return - true if has, false otherwise.
     */
    boolean hasNightMode();
}
