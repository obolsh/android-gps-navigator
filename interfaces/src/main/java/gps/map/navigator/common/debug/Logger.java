package gps.map.navigator.common.debug;

import androidx.annotation.NonNull;

public interface Logger {
    /**
     * print message in logcat if its debug build.
     *
     * @param message - text to print.
     */
    void debug(@NonNull String message);

    /**
     * print error message in logcat.
     *
     * @param message - text to print.
     */
    void error(@NonNull String message);

    /**
     * print throwable in logcat.
     *
     * @param throwable - text to print.
     */
    void error(@NonNull Throwable throwable);
}
