package com.example.framework.di.module;

import com.example.domain.interactor.feed.repo.FeedRepository;
import com.example.domain.interactor.user.repo.UserRepository;
import com.example.framework.api.feed.FeedApiService;
import com.example.framework.cache.CacheFile;
import com.example.framework.db.GobearDatabase;
import com.example.framework.di.scope.MainScope;
import com.example.framework.service.feed.FeedRepositoryImpl;
import com.example.framework.service.user.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    @MainScope
    UserRepository provideUserRepository(GobearDatabase gobearDatabase, CacheFile cacheFile) {
        return new UserRepositoryImpl(gobearDatabase, cacheFile);
    }

    @Provides
    @MainScope
    FeedRepository provideFeedRepository(GobearDatabase gobearDatabase, FeedApiService feedApiService) {
        return new FeedRepositoryImpl(gobearDatabase, feedApiService);
    }

    @Provides
    @MainScope
    FeedApiService provideFeedApiService(Retrofit retrofit) {
        return retrofit.create(FeedApiService.class);
    }
}
