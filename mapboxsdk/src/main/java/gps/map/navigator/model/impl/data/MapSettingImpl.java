package gps.map.navigator.model.impl.data;

import javax.inject.Inject;

import gps.map.navigator.model.MapType;
import gps.map.navigator.model.interfaces.MapSetting;

public class MapSettingImpl implements MapSetting {
    private String id;
    private int mapType;

    @Inject
    public MapSettingImpl() {
        super();
    }

    public MapSettingImpl(String id, int mapType) {
        this.id = id;
        this.mapType = mapType;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @MapType
    @Override
    public int getMapType() {
        return mapType;
    }

    @Override
    public void setMapType(@MapType int mapType) {
        this.mapType = mapType;
    }

    @Override
    public String toString() {
        return "MapSettingImpl{" +
                "id='" + id + '\'' +
                ", mapType=" + mapType +
                '}';
    }
}
