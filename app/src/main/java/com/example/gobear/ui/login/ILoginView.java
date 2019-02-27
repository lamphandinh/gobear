package com.example.gobear.ui.login;

import com.example.domain.model.User;
import com.example.gobear.ui.base.IBaseView;

public interface ILoginView extends IBaseView {
    void loginSuccess(User user);
    void loginFailed(String errorMessage);
    void wrongPassword();
}
