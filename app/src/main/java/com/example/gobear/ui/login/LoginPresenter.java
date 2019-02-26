package com.example.gobear.ui.login;

import com.example.domain.interactor.user.task.UserLoginUsecase;
import com.example.gobear.ui.base.BasePresenter;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<ILoginView> {

    @Inject
    UserLoginUsecase userLoginUsecase;

    public LoginPresenter(ILoginView view) {
        super(view);
    }

    public void login(String userName, String userPassword, boolean rememberMe) {

    }
}
