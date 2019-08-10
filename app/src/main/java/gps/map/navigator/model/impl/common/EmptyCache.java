package gps.map.navigator.model.impl.common;

import java.util.List;

import gps.map.navigator.model.interfaces.ICache;
import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IMapSetting;
import gps.map.navigator.model.interfaces.IRoute;

public class EmptyCache implements ICache {
    @Override
    public List<IMapPlace> getHistoryPlaces() {
        return null;
    }

    @Override
    public void setHistoryPlaces(List<IMapPlace> historyPlaces) {

    }

    @Override
    public IMapPlace getMyLocation() {
        return null;
    }

    @Override
    public void setMyLocation(IMapPlace myLocation) {

    }

    @Override
    public IMapPlace getLastOrigin() {
        return null;
    }

    @Override
    public void setLastOrigin(IMapPlace lastOrigin) {

    }

    @Override
    public IMapPlace getLastDestination() {
        return null;
    }

    @Override
    public void setLastDestination(IMapPlace lastDestination) {

    }

    @Override
    public IRoute getLastRoute() {
        return null;
    }

    @Override
    public void setLastRoute(IRoute lastRoute) {

    }

    @Override
    public IMapPlace getLastPlace() {
        return null;
    }

    @Override
    public void setLastPlace(IMapPlace lastPlace) {

    }

    @Override
    public IMapSetting getMapSettings() {
        return null;
    }

    @Override
    public void setMapSettings(IMapSetting mapSettings) {

    }

    @Override
    public byte[] getRawData(String key) {
        return new byte[0];
    }

    @Override
    public void setRawData(String key, byte[] rawData) {

    }
}
