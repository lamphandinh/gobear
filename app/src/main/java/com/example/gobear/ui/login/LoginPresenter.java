package com.example.gobear.ui.login;

import com.example.domain.communication.error.WrongPassException;
import com.example.domain.interactor.user.task.UserLoginUsecase;
import com.example.domain.model.User;
import com.example.gobear.R;
import com.example.gobear.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscriber;

public class LoginPresenter extends BasePresenter<ILoginView> {

    @Inject
    public UserLoginUsecase userLoginUsecase;

    public LoginPresenter(ILoginView view) {
        super(view);
    }

    public void login(String userName, String userPassword, boolean rememberMe) {
        mView.showLoading();
        userLoginUsecase.setUserData(userName, userPassword, rememberMe);
        executeTask(userLoginUsecase, new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.hideLoading();
                handleLoginError(e);
            }

            @Override
            public void onNext(User user) {
                mView.hideLoading();
                handleLoginSuccess(user);
            }
        }, true);
    }

    public void handleLoginError(Throwable e) {
        if (e instanceof WrongPassException) {
            mView.wrongPassword();
        } else {
            mView.loginFailed(mView.getContext().getString(R.string.error_login));
        }
    }

    public void handleLoginSuccess(User user) {
        if (user != null) mView.loginSuccess(user);
    }
}
