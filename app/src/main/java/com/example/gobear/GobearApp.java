package com.example.gobear;

import android.support.multidex.MultiDexApplication;

import com.example.framework.di.module.MainModule;
import com.example.gobear.di.component.ApplicationComponent;
import com.example.gobear.di.component.DaggerApplicationComponent;
import com.example.gobear.di.component.DaggerMainComponent;
import com.example.gobear.di.component.MainComponent;
import com.example.gobear.di.module.ApplicationModule;

public class GobearApp extends MultiDexApplication {

    private static GobearApp mApp;
    private ApplicationComponent applicationComponent;
    private MainComponent mainComponent;

    public static GobearApp get() { return mApp; }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initApplicationComponent();
    }

    private void initApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initMainComponent() {
        mainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
           initApplicationComponent();
        }
        return applicationComponent;
    }

    public MainComponent getMainComponent() {
        if (mainComponent == null) {
            initMainComponent();
        }
        return mainComponent;
    }
}
