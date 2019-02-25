package com.example.gobear.di.component;

import com.example.framework.di.module.MainModule;
import com.example.framework.di.scope.MainScope;
import com.example.gobear.ui.login.LoginPresenter;
import com.example.gobear.ui.main.MainPresenter;
import com.example.gobear.ui.splash.CheckLoginPresenter;

import dagger.Component;

@MainScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {MainModule.class}
)
public interface MainComponent {
    void inject(CheckLoginPresenter presenter);

    void inject(LoginPresenter presenter);

    void inject(MainPresenter presenter);
}
