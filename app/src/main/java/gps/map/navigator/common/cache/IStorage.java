package gps.map.navigator.common.cache;

import java.util.List;

public interface IStorage {

    boolean hasData(String key, boolean defaultValue);

    boolean saveData(String key, byte[] data);

    byte[] getData(String key, byte[] defaultValue);

    boolean saveChunkedData(List<byte[]> data);

    List<byte[]> getChunkedData();
}

