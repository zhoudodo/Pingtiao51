package com.pingtiao51.armsmodule.app.utils.interceptor;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;


public class HttpLoggerInterceptor implements HttpLoggingInterceptor.Logger {
    private final static String TAG = HttpLoggerInterceptor.class.getSimpleName();
    @Override
    public void log(String message) {
            Log.d(TAG, message);
    }
}
