package gps.map.navigator.common.cache;

import java.util.List;

public interface Storage {
    /**
     * check if any value exist in cache.
     *
     * @param key          - key for value.
     * @param defaultValue - default value if missing or error.
     * @return true if exist, or defaultValue.
     */
    boolean hasData(String key, boolean defaultValue);

    /**
     * Save value for key.
     *
     * @param key  - key.
     * @param data - byte array of data to be saved.
     * @return true if saved successfully, false otherwise.
     */
    boolean saveData(String key, byte[] data);

    /**
     * Get byte array from cache.
     *
     * @param key          - key for data.
     * @param defaultValue - default value if missing or error.
     * @return byte array from cache, or defaultValue.
     */
    byte[] getData(String key, byte[] defaultValue);

    /**
     * Save list of raw byte arrays.
     *
     * @param data - list of byte arrays to be saved.
     * @return true if saved successfully, false otherwise.
     */
    boolean saveChunkedData(List<byte[]> data);

    /**
     * Get list of raw byte arrays.
     *
     * @return list of byte arrayw or null if missing.
     */
    List<byte[]> getChunkedData();

    /**
     * Clean this instance references.
     */
    void invalidate();
}

