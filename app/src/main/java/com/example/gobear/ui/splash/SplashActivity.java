package com.example.gobear.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.gobear.BuildConfig;
import com.example.gobear.GobearApp;
import com.example.gobear.R;
import com.example.gobear.ui.base.BaseActivity;
import com.example.gobear.ui.login.LoginActivity;
import com.example.gobear.ui.main.MainActivity;
import com.example.gobear.ui.walkthough.WalkThroughActivity;
import com.example.gobear.util.GobearAppCache;

public class SplashActivity extends BaseActivity<CheckLoginPresenter> implements IFirstRouterView {
    private static final int SPLASH_TIME = BuildConfig.DEBUG ? 1000 : 500;
    private CheckLoginPresenter mPresenter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.checkLogin();
            }
        }, SPLASH_TIME);
        if (BuildConfig.DEBUG) {
            mPresenter.inputDebugData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected CheckLoginPresenter createPresenter() {
        mPresenter = new CheckLoginPresenter(this, GobearAppCache.getInstance(this));
        GobearApp.get().getMainComponent().inject(mPresenter);
        return mPresenter;
    }

    @Override
    public void moveToWalkThrough() {
        Intent walkThroughIntent = new Intent(this, WalkThroughActivity.class);
        startActivity(walkThroughIntent);
        finish();
    }

    @Override
    public void moveToLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void moveToMain() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public Context getContext() {
        return null;
    }
}
