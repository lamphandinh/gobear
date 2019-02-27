package com.example.gobear.util;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.gobear.GobearApp;
import com.example.gobear.R;

public final class UIUtils {

    private static UIUtils mHelper;

    private Context mContext;

    private UIUtils(Context context) {
        mContext = context;
    }

    private static UIUtils get() {
        if (mHelper == null) {
            synchronized (UIUtils.class) {
                if (mHelper == null) {
                    mHelper = new UIUtils(GobearApp.get());
                }
            }
        }
        return mHelper;
    }

    public static void showShortToast(CharSequence msg) {
        if (msg == null || msg.length() == 0) return;
        Toast.makeText(get().mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(@StringRes int msg) {
        Toast.makeText(get().mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(CharSequence msg) {
        if (msg == null || msg.length() == 0) return;
        Toast.makeText(get().mContext, msg, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(@StringRes int msg) {
        Toast.makeText(get().mContext, msg, Toast.LENGTH_LONG).show();
    }

    public static void hideKeyboard(View v) {
        if (v == null) return;

        Context context = v.getContext();
        if (context == null) return;

        IBinder token = v.getWindowToken();
        if (token == null) return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(token, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void showKeyboard(View v) {
        if (v == null) return;

        Context context = v.getContext();
        if (context == null) return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }

    public static long getLongFromString(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
        }
        return 0L;
    }

    public static int getIntFromString(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public static void shakeView(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_shake));
    }
}
