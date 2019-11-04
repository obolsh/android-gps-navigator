package gps.map.navigator.common.debug;

public interface Logger {

    void debug(String message);

    void error(String message);

    void error(Throwable throwable);
}
