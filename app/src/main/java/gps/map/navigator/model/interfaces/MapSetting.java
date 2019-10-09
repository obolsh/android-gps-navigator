package gps.map.navigator.model.interfaces;

import java.io.Serializable;

import gps.map.navigator.model.MapType;

public interface MapSetting extends Serializable {

    void setId(String id);

    String getId();

    @MapType
    int getMapType();

    void setMapType(@MapType int mapType);
}
