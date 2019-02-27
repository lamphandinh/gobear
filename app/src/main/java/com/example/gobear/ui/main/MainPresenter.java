package com.example.gobear.ui.main;

import com.example.domain.interactor.feed.task.FeedUseCase;
import com.example.domain.model.Feed;
import com.example.gobear.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class MainPresenter extends BasePresenter<IMainView> {

    @Inject
    FeedUseCase feedUseCase;

    public MainPresenter(IMainView view) {
        super(view);
    }

    public void loadFeeds() {
        mView.showLoading();
        executeTask(feedUseCase, new Subscriber<List<Feed>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadedFeedsFailed(e.getMessage());
            }

            @Override
            public void onNext(List<Feed> feeds) {
                mView.loadedFeeds(feeds);
            }
        });
    }
}
