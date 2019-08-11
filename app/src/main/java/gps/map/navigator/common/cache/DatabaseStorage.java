package gps.map.navigator.common.cache;

import java.util.List;

public class DatabaseStorage implements IStorage {
    @Override
    public boolean hasData(String key, boolean defaultValue) {
        return false;
    }

    @Override
    public boolean saveData(String key, byte[] data) {
        return false;
    }

    @Override
    public byte[] getData(String key, byte[] defaultValue) {
        return new byte[0];
    }

    @Override
    public boolean saveChunkedData(List<byte[]> data) {
        return false;
    }

    @Override
    public List<byte[]> getChunkedData() {
        return null;
    }
}
