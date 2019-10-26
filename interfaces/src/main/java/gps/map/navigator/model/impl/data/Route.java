package gps.map.navigator.model.impl.data;

import gps.map.navigator.model.interfaces.IMapPlace;
import gps.map.navigator.model.interfaces.IRoute;

public class Route implements IRoute {

    private String id = "default_id";
    private IMapPlace origin;
    private IMapPlace destination;
    private String title;
    private long lastUsedTime;

    public Route() {
    }

    public Route(String id, IMapPlace origin, IMapPlace destination, String title,
                 long lastUsedTime) {
        super();
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.title = title;
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
    public IMapPlace getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(IMapPlace origin) {
        this.origin = origin;
    }

    @Override
    public IMapPlace getDestination() {
        return destination;
    }

    @Override
    public void setDestination(IMapPlace destination) {
        this.destination = destination;
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
    public long getLastUsedTime() {
        return lastUsedTime;
    }

    @Override
    public void setLastUsedTime(long lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", title='" + title + '\'' +
                ", lastUsedTime=" + lastUsedTime +
                '}';
    }
}
