package gps.map.navigator.model.interfaces;

        import java.io.Serializable;

public interface IRoute extends Serializable {

    String getId();

    void setId(String id);

    IMapPlace getOrigin();

    void setOrigin(IMapPlace origin);

    IMapPlace getDestination();

    void setDestination(IMapPlace destination);

    String getTitle();

    void setTitle(String title);

    long getLastUsedTime();

    void setLastUsedTime(long lastUsedTime);
}
