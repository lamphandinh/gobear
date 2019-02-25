package com.example.gobear.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;

public class NetworkUtil {
    private static Context sCtx;
    public static void init(Context context) {
        sCtx = context.getApplicationContext();
    }

    public static boolean isConnected() {
        boolean isDozing = isDozing(sCtx);
        NetworkInfo info = getNetworkInfo(sCtx);
        return !isDozing && info != null && info.isConnected();
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (cm == null) {
            return null;
        }
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return null;
        }
        return ni;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isDozing(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            return powerManager.isDeviceIdleMode();
        } else {
            return false;
        }
    }
}
