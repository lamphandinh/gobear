package com.example.gobear.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity<V extends BasePresenter> extends AppCompatActivity implements IBaseView{

    protected V mPresenter;
    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

    private final int VIEW_FLAGS = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    protected boolean isResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter();
        enterImmersiveMode();
    }

    private void initPresenter() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        mPresenter = createPresenter();
    }

    protected abstract V createPresenter();

    private void enterImmersiveMode() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(VIEW_FLAGS);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(VIEW_FLAGS);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResume = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enterImmersiveMode();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSubscriptions.unsubscribe();

        if (mPresenter != null) mPresenter.onDestroy();
    }



    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }
}
