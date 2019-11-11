package gps.map.navigator.common.debug;

import android.util.Log;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import gps.map.navigator.BuildConfig;

public class LoggerImpl implements Logger {

    private static final String TAG = "gps_tag";

    @Inject
    LoggerImpl() {
    }

    @Override
    public void debug(@NonNull String message) {
        if (BuildConfig.DEBUG) {
            printInLogcat(message, false);
        }
    }

    @Override
    public void error(@NonNull String message) {
        printInLogcat(message, true);
    }

    @Override
    public void error(@NonNull Throwable throwable) {
        Log.e(TAG, "", throwable);
    }

    private void printInLogcat(@NonNull String message, boolean isError) {
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

    private void log(@NonNull String message) {
        Log.e(TAG, message);
    }

    private void exception(@NonNull String message) {
        Log.e(TAG, message);
    }
}
