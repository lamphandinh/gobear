package com.example.domain.interactor.feed.repo;

import com.example.domain.model.Feed;

import java.util.List;

import rx.Observable;

public interface FeedRepository {
    Observable<List<Feed>> getAllLocalFeeds();
    Observable<Boolean> replaceAllLocalFeeds(List<Feed> feeds);
    Observable<List<Feed>> getNewestFeedsFromServer();
}
