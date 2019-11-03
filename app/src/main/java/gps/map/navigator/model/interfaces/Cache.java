package gps.map.navigator.model.interfaces;

import java.util.List;

public interface Cache {

    List<IMapPlace> getHistoryPlaces();

    void setHistoryPlaces(List<IMapPlace> historyPlaces);

    IMapPlace getMyLocation();

    void setMyLocation(IMapPlace myLocation);

    IMapPlace getLastOrigin();

    void setLastOrigin(IMapPlace lastOrigin);

    IMapPlace getLastDestination();

    void setLastDestination(IMapPlace lastDestination);

    IRoute getLastRoute();

    void setLastRoute(IRoute lastRoute);

    IMapPlace getLastPlace();

    void setLastPlace(IMapPlace lastPlace);

    MapSetting getMapSettings();

    void setMapSettings(MapSetting mapSettings);

    byte[] getRawData(String key);

    void setRawData(String key, byte[] rawData);

    void removeHistoryPlace(IMapPlace placeToRemove);
}
