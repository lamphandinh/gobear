package com.example.domain.interactor.feed;

import com.example.domain.interactor.BaseUnitTest;
import com.example.domain.interactor.feed.repo.FeedRepository;
import com.example.domain.interactor.feed.task.FeedUseCase;
import com.example.domain.model.Feed;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.when;

public class FeedTest extends BaseUnitTest {
    private FeedUseCase feedUseCase;

    @Mock
    FeedRepository mockFeedRepo;

    @Before
    public void setup() throws Exception {
        super.setup();
        feedUseCase = new FeedUseCase(mockBatchExecutor, mockPostExecutionThread, mockFeedRepo);
    }

    @Test
    public void shouldAlwaysShowOfflineDataFirst() {
        final List<Feed> expectLocalFeeds = Arrays.asList(new Feed("test", "", "", ""));
        when(mockFeedRepo.getAllLocalFeeds()).thenReturn(
                Observable.fromCallable(new Callable<List<Feed>>() {
                    @Override
                    public List<Feed> call() throws Exception {
                        return expectLocalFeeds;
                    }
                })
        );
        TestSubscriber<List<Feed>> subscriber = subscribeOnTask(feedUseCase);
        subscriber.assertNoErrors();
        List<Feed> result = subscriber.getOnNextEvents().get(0);
        Assert.assertEquals(expectLocalFeeds.size(), result.size());
        Assert.assertEquals(expectLocalFeeds.get(0).getTitle(), result.get(0).getTitle());
    }

    @Test
    public void shouldAlwaysShowNewestDataAfterGetItFromServer() {
        final List<Feed> expectLocalFeeds = Arrays.asList(new Feed("test_local", "", "", ""));
        final List<Feed> expectRemoteFeeds = Arrays.asList(new Feed("test_remote", "", "", ""));
        when(mockFeedRepo.getAllLocalFeeds()).thenReturn(
                Observable.fromCallable(new Callable<List<Feed>>() {
                    @Override
                    public List<Feed> call() throws Exception {
                        return expectLocalFeeds;
                    }
                })
        );
        when(mockFeedRepo.getNewestFeedsFromServer()).thenReturn(
                Observable.fromCallable(new Callable<List<Feed>>() {
                    @Override
                    public List<Feed> call() throws Exception {
                        return expectLocalFeeds;
                    }
                })
        );
        TestSubscriber<List<Feed>> subscriber = subscribeOnTask(feedUseCase);
        subscriber.assertNoErrors();
        List<Feed> result = subscriber.getOnNextEvents().get(1);
        Assert.assertEquals(expectRemoteFeeds.size(), result.size());
        Assert.assertEquals(expectRemoteFeeds.get(0).getTitle(), result.get(0).getTitle());
    }
}
