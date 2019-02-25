package com.example.domain.interactor;

import com.example.domain.executor.PostExecutionThread;

import java.util.concurrent.Executor;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func2;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class BaseTask<T> {

    private final Executor executor;
    private final PostExecutionThread postExecutionThread;
    public static final EmptySubscriber EMPTY_SUBSCRIBER = new EmptySubscriber();
    private static final ObservableHooks HOOKS = new ObservableHooks("null");

    private Subscription subscription = Subscriptions.empty();

    static {
        RxJavaHooks.setOnObservableStart(HOOKS);
    }

    protected BaseTask(Executor executor,
                       PostExecutionThread postExecutionThread) {
        this.executor = executor;
        this.postExecutionThread = postExecutionThread;
    }


    protected abstract Observable<T> buildUseCaseObservable();

    private Observable<T> wrapObservable() {
        HOOKS.setCurrentTask(BaseTask.this.getClass().getName());
        return this.buildUseCaseObservable();
    }


    public Subscription execute(Subscriber<T> useCaseSubscriber) {
        subscription = wrapObservable().subscribeOn(Schedulers.from(executor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(useCaseSubscriber);
        return subscription;
    }


    public Subscription execute(Subscriber<T> userSubscriber, boolean cancelPrevious) {
        if (cancelPrevious && subscription != null) {
            subscription.unsubscribe();
        }
        return execute(userSubscriber);
    }


    public Subscription executeOnEmptySubscriber() {
        subscription = wrapObservable()
                .subscribeOn(Schedulers.from(executor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(EMPTY_SUBSCRIBER);
        return subscription;
    }


    public Subscription executeOnEmptySubscriber(boolean cancelPrevious) {
        if (cancelPrevious && subscription != null) {
            subscription.unsubscribe();
        }
        return executeOnEmptySubscriber();
    }


    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private static class EmptySubscriber implements Observer {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Object o) {

        }
    }

    private static class ObservableHooks implements Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe> {

        private String currentTask;

        ObservableHooks(String taskName) {
            this.currentTask = taskName;
        }

        public void setCurrentTask(String currentTask) {
            this.currentTask = currentTask;
        }

        @Override
        public Observable.OnSubscribe call(Observable observable, Observable.OnSubscribe onSubscribe) {
            return onSubscribe;
        }
    }

}
