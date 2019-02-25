package com.example.gobear.di.component;

import com.example.framework.di.module.MainModule;
import com.example.framework.di.scope.MainScope;
import com.example.gobear.ui.MainPresenter;

import dagger.Component;

@MainScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {MainModule.class}
)
public interface MainComponent {
    void inject(MainPresenter presenter);
}
