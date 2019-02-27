package com.example.framework.service.feed;

import com.example.domain.interactor.feed.repo.FeedRepository;
import com.example.domain.model.Feed;
import com.example.framework.db.GobearDatabase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class FeedRepositoryImpl implements FeedRepository {

    private GobearDatabase gobearDatabase;

    @Inject
    public FeedRepositoryImpl(GobearDatabase gobearDatabase) {
        this.gobearDatabase = gobearDatabase;
    }

    @Override
    public Observable<List<Feed>> getAllLocalFeeds() {
        return null;
    }

    @Override
    public Observable<List<Feed>> getNewestFeedsFromServer() {
        return null;
    }
}
