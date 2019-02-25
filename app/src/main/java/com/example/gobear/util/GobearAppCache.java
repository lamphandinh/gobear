package com.example.gobear.util;

import android.content.Context;
import android.content.SharedPreferences;

public class GobearAppCache {
    public static final String GOBEAR_APP_CACHE = "gobear";
    public static final String IS_FIRST_TIME = "fisrt";
    private static SharedPreferences mSharedPref;
    private static volatile GobearAppCache instance;
    private GobearAppCache(){
    }
    public static GobearAppCache getInstance(Context context) {
        if (instance == null) {
            synchronized (GobearAppCache.class) {
                if (instance == null) {
                    instance = new GobearAppCache();
                    mSharedPref = context.getSharedPreferences(GOBEAR_APP_CACHE, Context.MODE_PRIVATE);
                }
            }
        }
        return instance;
    }

    public boolean isFirstTime() {
        return mSharedPref.getBoolean(IS_FIRST_TIME, true);
    }

    public void userOpenedApp() {
        mSharedPref.edit().putBoolean(IS_FIRST_TIME, false).apply();
    }
}
