package com.example.gobear.ui.splash;

import com.example.domain.interactor.user.task.LastUserUsecase;
import com.example.gobear.ui.base.BasePresenter;
import com.example.gobear.util.GobearAppCache;

import javax.inject.Inject;

public class CheckLoginPresenter extends BasePresenter<IFirstRouterView> {
    @Inject
    LastUserUsecase lastUserUsecase;

    GobearAppCache gobearAppCache;

    public CheckLoginPresenter(IFirstRouterView view, GobearAppCache gobearAppCache) {
        super(view);
        this.gobearAppCache = gobearAppCache;
    }

    public void checkLogin() {
    }
}
