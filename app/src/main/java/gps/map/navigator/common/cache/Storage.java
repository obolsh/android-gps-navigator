package gps.map.navigator.common.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface Storage {
    /**
     * check if any value exist in cache.
     *
     * @param key          - key for value.
     * @param defaultValue - default value if missing or error.
     * @return true if exist, or defaultValue.
     */
    boolean hasData(@NonNull String key, boolean defaultValue);

    /**
     * Save value for key.
     *
     * @param key  - key.
     * @param data - byte array of data to be saved.
     * @return true if saved successfully, false otherwise.
     */
    boolean saveData(@NonNull String key, @Nullable byte[] data);

    /**
     * Get byte array from cache.
     *
     * @param key          - key for data.
     * @param defaultValue - default value if missing or error.
     * @return byte array from cache, or defaultValue.
     */
    byte[] getData(@NonNull String key, @Nullable byte[] defaultValue);

    /**
     * Save list of raw byte arrays.
     *
     * @param data - list of byte arrays to be saved.
     * @return true if saved successfully, false otherwise.
     */
    boolean saveChunkedData(@NonNull List<byte[]> data);

    /**
     * Get list of raw byte arrays.
     *
     * @return list of byte arrayw or null if missing.
     */
    @Nullable
    List<byte[]> getChunkedData();

    /**
     * Clean this instance references.
     */
    void invalidate();
}

