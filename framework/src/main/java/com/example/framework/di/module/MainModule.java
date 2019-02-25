package com.example.framework.di.module;

import com.example.domain.interactor.user.repo.UserRepository;
import com.example.framework.cache.CacheFile;
import com.example.framework.db.GobearDatabase;
import com.example.framework.di.scope.MainScope;
import com.example.framework.service.user.UserRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    @MainScope
    UserRepository provideUserRepository(GobearDatabase gobearDatabase, CacheFile cacheFile) {
        return new UserRepositoryImpl(gobearDatabase, cacheFile);
    }
}
