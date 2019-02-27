package com.example.domain.interactor.feed.repo;

import com.example.domain.model.Feed;

import java.util.List;

import rx.Observable;

public interface FeedRepository {
    Observable<List<Feed>> getAllLocalFeeds();
    Observable<List<Feed>> getNewestFeedsFromServer();
}
