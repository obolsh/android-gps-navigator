package gps.map.navigator.model.interfaces;

import java.io.Serializable;

public interface IMapSettings extends Serializable {

    void setId(String id);

    boolean isNightMode();

    void setNightMode(boolean nightMode);

    int getMapType();

    void setMapType(int mapType);
}
