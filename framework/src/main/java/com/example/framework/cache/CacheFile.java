package com.example.framework.cache;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheFile {
    private static final String LAST_USER_TOKEN = "a";
    private SharedPreferences mSharedPreferences;

    public CacheFile(Context context) {
        mSharedPreferences = context.getSharedPreferences("GOBEAR_CACHE_FILE", Context.MODE_PRIVATE);
    }

    public String getLastUserToken() {
        return mSharedPreferences.getString(LAST_USER_TOKEN, "");
    }

    public void setLastUserToken(String token) {
        mSharedPreferences.edit().putString(LAST_USER_TOKEN, token).apply();
    }
}
