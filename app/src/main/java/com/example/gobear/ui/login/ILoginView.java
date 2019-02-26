package com.example.gobear.ui.login;

import com.example.gobear.ui.base.IBaseView;

public interface ILoginView extends IBaseView {
    void loginSuccess();
    void loginFailed(String errorMessage);
}
