package com.example.gobear.di.module;

import android.app.Application;

import com.example.domain.communication.EventBus;
import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.RemoteIOExecutor;
import com.example.domain.executor.UITaskExecutor;
import com.example.framework.eventbus.RxBusImpl;
import com.example.framework.executor.BatchJobExecutor;
import com.example.framework.executor.NetworkJobExecutor;
import com.example.framework.executor.UIOperatorExecutor;
import com.example.gobear.GobearApp;
import com.example.gobear.executor.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final GobearApp gobearApp;

    public ApplicationModule(GobearApp gobearApp) {
        this.gobearApp = gobearApp;
    }

    @Provides
    @Singleton
    Application provideApplicationContext() { return gobearApp; }

    @Provides
    @Singleton
    BatchExecutor provideBatchJobExecutor(BatchJobExecutor batchJobExecutor) {
        return batchJobExecutor;
    }

    @Provides
    @Singleton
    RemoteIOExecutor provideNetworkExecutor(NetworkJobExecutor networkJobExecutor) {
        return networkJobExecutor;
    }

    @Provides
    @Singleton
    UITaskExecutor provideUITaskExecutor(UIOperatorExecutor uiOperatorExecutor) {
        return uiOperatorExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    EventBus provideEventBus() { return new RxBusImpl(); }
}
