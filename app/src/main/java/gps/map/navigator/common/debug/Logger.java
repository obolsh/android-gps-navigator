package gps.map.navigator.common.debug;

import android.util.Log;

import gps.map.navigator.BuildConfig;

public class Logger {

    private static final String TAG = "gps_tag";

    public static void debug(String message) {
        if (BuildConfig.DEBUG) {
            printInLogcat(message, false);
        }
    }

    public static void error(String message) {
        printInLogcat(message, true);
    }

    public static void error(Throwable throwable) {
        Log.e(TAG, "", throwable);
    }

    private static void printInLogcat(String message, boolean isError) {
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

    private static void log(String message) {
        Log.e(TAG, message);
    }

    private static void exception(String message) {
        Log.e(TAG, message);
    }
}
