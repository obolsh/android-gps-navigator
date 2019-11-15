package gps.map.navigator.model.impl.data;

import gps.map.navigator.model.interfaces.IMapPlace;

public class MapPlace implements IMapPlace {

    private String id = "default_id";
    private double x;
    private double y;
    private String title;
    private String address;
    private String label;
    private boolean favourite;
    private long lastUsedTime;

    public MapPlace() {
    }

    public MapPlace(String id, double x, double y, String title, String address, String label,
                    boolean favourite, long lastUsedTime) {
        super();
        this.id = id;
        this.x = x;
        this.y = y;
        this.title = title;
        this.address = address;
        this.label = label;
        this.favourite = favourite;
        this.lastUsedTime = lastUsedTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public double getLongitude() {
        return x;
    }

    @Override
    public void setLongitude(double longitude) {
        this.x = longitude;
    }

    @Override
    public double getLatitude() {
        return y;
    }

    @Override
    public void setLatitude(double y) {
        this.y = y;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean isFavourite() {
        return favourite;
    }

    @Override
    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public long getLastUsedTime() {
        return lastUsedTime;
    }

    @Override
    public void setLastUsedTime(long lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    @Override
    public String toString() {
        return "MapPlace{" +
                "id='" + id + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", label='" + label + '\'' +
                ", favourite=" + favourite +
                ", lastUsedTime=" + lastUsedTime +
                '}';
    }
}
