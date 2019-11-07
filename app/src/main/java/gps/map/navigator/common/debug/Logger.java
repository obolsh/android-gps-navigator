package gps.map.navigator.common.debug;

public interface Logger {
    /**
     * print message in logcat if its debug build.
     *
     * @param message - text to print.
     */
    void debug(String message);

    /**
     * print error message in logcat.
     *
     * @param message - text to print.
     */
    void error(String message);

    /**
     * print throwable in logcat.
     *
     * @param throwable - text to print.
     */
    void error(Throwable throwable);
}
