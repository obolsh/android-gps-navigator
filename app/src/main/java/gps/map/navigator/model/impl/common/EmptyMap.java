package gps.map.navigator.model.impl.common;

import gps.map.navigator.model.interfaces.IMapSetting;

public class EmptyMap implements IMapSetting {
    @Override
    public void setId(String id) {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public int getMapType() {
        return 0;
    }

    @Override
    public void setMapType(int mapType) {

    }
}
