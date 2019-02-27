package com.example.gobear.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.domain.model.User;
import com.example.gobear.GobearApp;
import com.example.gobear.R;
import com.example.gobear.ui.base.BaseActivity;
import com.example.gobear.ui.main.MainActivity;
import com.example.gobear.util.UIUtils;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

    EditText etUserName;
    EditText etPassword;
    TextInputLayout tilPassword;
    CheckBox cbRememberMe;
    Button btnLogin;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPwd);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPwd);
        cbRememberMe = (CheckBox) findViewById(R.id.cb_remember_me);
        btnLogin = (Button) findViewById(R.id.btn_sign_in);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login(etUserName.getText().toString(), etPassword.getText().toString(),
                        cbRememberMe.isChecked());
            }
        });
        etUserName.requestFocus();
        UIUtils.showKeyboard(etUserName);
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

    @Override
    public void loginSuccess(User user) {
        UIUtils.hideKeyboard(etUserName);
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void loginFailed(String errorMessage) {
        UIUtils.showShortToast(errorMessage);
    }

    @Override
    public void wrongPassword() {
        UIUtils.showShortToast(R.string.error_wrong_pass);
        UIUtils.shakeView(tilPassword);
    }

    @VisibleForTesting
    public void setPresenter(LoginPresenter loginPresenter) {
        mPresenter = loginPresenter;
    }
}
