package com.example.gobear.ui.base;

import com.example.domain.interactor.BaseTask;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V extends IBaseView> {

    protected V mView = null;
    private CompositeSubscription mSubscription = new CompositeSubscription();

    public BasePresenter(V view) {
        mView = view;
    }

    protected <T> Subscription executeTask(BaseTask<T> task, Subscriber<T> subscriber) {
        return executeTask(task, subscriber, true);
    }

    protected Subscription executeTask(BaseTask task, Subscriber subscriber, boolean unSubscribeOnDestroy) {
        Subscription subscription = null;
        if (subscriber != null) {
            subscription = task.execute(subscriber);
        }
        if (unSubscribeOnDestroy && subscription != null) {
            mSubscription.add(subscription);
        }
        return subscription;
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onAttach(V callback) {
        mView = callback;
    }

    public void onDetach() {
        mView = null;
    }

    public void onDestroy() {
        mSubscription.clear();
    }

}
