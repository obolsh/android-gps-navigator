package gps.map.navigator.model.impl.data;

import gps.map.navigator.model.MapType;
import gps.map.navigator.model.interfaces.IMapSetting;

public class MapSetting implements IMapSetting {

    private String id = "default_id";
    private int mapType;

    public MapSetting() {
    }

    public MapSetting(String id, int mapType) {
        super();
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
        return "MapSetting{" +
                "id='" + id + '\'' +
                ", mapType=" + mapType +
                '}';
    }
}
