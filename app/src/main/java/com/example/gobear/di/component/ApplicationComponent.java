package com.example.gobear.di.component;

import android.app.Application;

import com.example.domain.communication.EventBus;
import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.RemoteIOExecutor;
import com.example.domain.executor.UITaskExecutor;
import com.example.framework.di.module.DbModule;
import com.example.framework.di.module.NetworkModule;
import com.example.gobear.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, DbModule.class})
public interface ApplicationComponent {
    Application application();

    BatchExecutor batchExecutor();

    PostExecutionThread postExecutionThread();

    RemoteIOExecutor remoteIOExecutor();

    UITaskExecutor uiTaskExecutor();

    Retrofit retrofit();

    EventBus eventBus();
}
