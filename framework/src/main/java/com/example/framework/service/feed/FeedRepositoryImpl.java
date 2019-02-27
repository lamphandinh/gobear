package com.example.framework.service.feed;

import com.example.domain.interactor.feed.repo.FeedRepository;
import com.example.domain.model.Feed;
import com.example.framework.api.feed.FeedApiService;
import com.example.framework.api.feed.FeedResponse;
import com.example.framework.api.feed.FeedRssItem;
import com.example.framework.db.GobearDatabase;
import com.example.framework.db.model.DBFeed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class FeedRepositoryImpl implements FeedRepository {

    private GobearDatabase gobearDatabase;
    private FeedApiService feedApiService;

    @Inject
    public FeedRepositoryImpl(GobearDatabase gobearDatabase, FeedApiService feedApiService) {
        this.gobearDatabase = gobearDatabase;
        this.feedApiService = feedApiService;
    }

    @Override
    public Observable<List<Feed>> getAllLocalFeeds() {
        return Observable.fromCallable(new Callable<List<Feed>>() {
            @Override
            public List<Feed> call() throws Exception {
                List<Feed> result = new ArrayList<>();
                DBFeed[] localFeeds = gobearDatabase.FeedDao().loadALlFeeds();
                for (DBFeed dbFeed : localFeeds) {
                    result.add(new Feed(dbFeed.getTitle(), dbFeed.getDescription(), dbFeed.getDetailUrl(), dbFeed.getThumbnailUrl()));
                }
                return result;
            }
        });
    }

    @Override
    public Observable<List<Feed>> getNewestFeedsFromServer() {
        return feedApiService.getFeeds().map(new Func1<FeedResponse, List<Feed>>() {
            @Override
            public List<Feed> call(FeedResponse feedResponse) {
                List<Feed> feeds = new ArrayList<>();
                for (FeedRssItem item : feedResponse.channel.feedRssItemList) {
                    feeds.add(new Feed(item.title, item.description, item.detailUrl, item.thumbnailUrl));
                }
                return feeds;
            }
        })
    }

    @Override
    public Observable<Boolean> replaceAllLocalFeeds(List<Feed> feeds) {
        gobearDatabase.FeedDao().deleteFeeds();
        List<DBFeed> dbFeeds = new ArrayList<>();
        for (Feed feed : feeds) {
            dbFeeds.add(new DBFeed(feed.getTitle(), feed.getDescription(), feed.getDetailUrl(), feed.getThumbnailUrl()));
        }
        gobearDatabase.FeedDao().insertFeeds(dbFeeds);
        return true;
    }
}
