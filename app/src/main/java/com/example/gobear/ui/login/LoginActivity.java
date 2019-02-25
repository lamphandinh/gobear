package com.example.gobear.ui.login;

import android.content.Context;
import android.os.Bundle;

import com.example.gobear.GobearApp;
import com.example.gobear.R;
import com.example.gobear.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected LoginPresenter createPresenter() {
        mPresenter = new LoginPresenter(this);
        GobearApp.get().getMainComponent().inject(mPresenter);
        return mPresenter;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
