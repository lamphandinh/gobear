package com.example.domain.interactor.feed.task;

import com.example.domain.executor.BatchExecutor;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.interactor.BaseTask;
import com.example.domain.interactor.feed.repo.FeedRepository;
import com.example.domain.model.Feed;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func2;

public class FeedUseCase extends BaseTask<List<Feed>> {

    private FeedRepository feedRepository;

    @Inject
    public FeedUseCase(BatchExecutor executor, PostExecutionThread postExecutionThread,
                       FeedRepository feedRepository) {
        super(executor, postExecutionThread);
        this.feedRepository = feedRepository;
    }

    @Override
    protected Observable<List<Feed>> buildUseCaseObservable() {
        return Observable.combineLatest(feedRepository.getAllLocalFeeds(), feedRepository.getNewestFeedsFromServer(),
                new Func2<List<Feed>, List<Feed>, List<Feed>>() {
                    @Override
                    public List<Feed> call(List<Feed> offlineFeeds, List<Feed> remoteFeeds) {
                        if (remoteFeeds != null) return remoteFeeds;
                        return offlineFeeds;
                    }
                });
    }
}
