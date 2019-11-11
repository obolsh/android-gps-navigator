package gps.map.navigator.model.interfaces;

import java.io.Serializable;

public interface IMapPlace extends Serializable {

    String getId();

    void setId(String id);

    long getX();

    void setX(long x);

    long getY();

    void setY(long y);

    String getTitle();

    void setTitle(String title);

    String getAddress();

    void setAddress(String address);

    String getLabel();

    void setLabel(String label);

    boolean isFavourite();

    void setFavourite(boolean favourite);

    long getLastUsedTime();

    void setLastUsedTime(long lastUsedTime);
}
