package gps.map.navigator.common.debug;

import android.util.Log;

import javax.inject.Inject;

import gps.map.navigator.BuildConfig;

public class LoggerImpl implements Logger {

    private static final String TAG = "gps_tag";

    @Inject
    LoggerImpl() {
    }

    public void debug(String message) {
        if (BuildConfig.DEBUG) {
            printInLogcat(message, false);
        }
    }

    public void error(String message) {
        printInLogcat(message, true);
    }

    public void error(Throwable throwable) {
        Log.e(TAG, "", throwable);
    }

    private void printInLogcat(String message, boolean isError) {
        int maxLogSize = 2000;
        String text;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            text = message.substring(start, end);
            if (isError) {
                exception(text);
            } else {
                log(text);
            }
        }
    }

    private void log(String message) {
        Log.e(TAG, message);
    }

    private void exception(String message) {
        Log.e(TAG, message);
    }
}
