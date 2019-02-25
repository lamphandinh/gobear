package com.example.framework.di.module;

import android.app.Application;

import com.example.framework.cache.CacheFile;
import com.example.framework.db.GobearDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    GobearDatabase provideGobearDatabase(Application application) {
        return GobearDatabase.getInstance(application);
    }

    @Provides
    @Singleton
    CacheFile provideCacheFile(Application application) {
        return new CacheFile(application.getApplicationContext());
    }
}
