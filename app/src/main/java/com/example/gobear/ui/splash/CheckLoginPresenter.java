package com.example.gobear.ui.splash;

import com.example.domain.interactor.user.task.LastUserUsecase;
import com.example.domain.model.User;
import com.example.gobear.ui.base.BasePresenter;
import com.example.gobear.util.GobearAppCache;

import javax.inject.Inject;

import rx.Subscriber;

public class CheckLoginPresenter extends BasePresenter<IFirstRouterView> {
    @Inject
    public LastUserUsecase lastUserUsecase;

    GobearAppCache gobearAppCache;

    public CheckLoginPresenter(IFirstRouterView view, GobearAppCache gobearAppCache) {
        super(view);
        this.gobearAppCache = gobearAppCache;
    }

    public void checkLogin() {
        if (gobearAppCache.isFirstTime()) {
            gobearAppCache.userOpenedApp();
            mView.moveToWalkThrough();
        } else {
            executeTask(lastUserUsecase, new Subscriber<User>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(User user) {
                    moveBaseOnUser(user);
                }
            }, true);
        }
    }

    public void moveBaseOnUser(User user) {
        if (user == null) {
            mView.moveToLogin();
        } else {
            mView.moveToMain();
        }
    }
}
