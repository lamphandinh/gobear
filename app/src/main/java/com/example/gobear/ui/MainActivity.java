package com.example.gobear.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.gobear.GobearApp;
import com.example.gobear.R;
import com.example.gobear.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected MainPresenter createPresenter() {
        mainPresenter = new MainPresenter(this);
        GobearApp.get().getMainComponent().inject(mainPresenter);
        return mainPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
