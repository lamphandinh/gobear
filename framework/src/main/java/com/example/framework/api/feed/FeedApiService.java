package com.example.framework.api.feed;

import retrofit2.http.GET;
import rx.Observable;

public class FeedApiService {
    @GET("/news/world/asia/rss.xml")
    public Observable<FeedResponse> getFeeds();
}
