package com.example.framework.eventbus;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.domain.communication.EventBus;

import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

public class RxBusImpl implements EventBus {

    private SerializedSubject<Object, Object> mBus;
    private final HashMap<String, CompositeSubscription> mSubscriptionMap = new HashMap<>(32);

    @Inject
    public RxBusImpl() {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }

    public void post(Object o) {
        Log.d("[event][bus]",o.getClass().getName());
        mBus.onNext(o);
    }

    private <T> Observable<T> toObservable(final Class<T> type) {
        return mBus.ofType(type).subscribeOn(Schedulers.computation());
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public <T> Subscription register(Class<T> type, Subscriber<T> subscriber) {
        Subscription subscription = toObservable(type)
                .onBackpressureBuffer()
                .subscribe(subscriber);
        addSubscription(type, subscription);
        return subscription;
    }

    public <T> Subscription registerOnSpecificThread(final Class<T> type, Scheduler sub, Scheduler obs, Subscriber<T> subscriber) {
        Subscription subscription = mBus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return o!=null && o.getClass().getName().equals(type.getName());
            }
        }).map(new Func1<Object, T>() {
            @Override
            public T call(Object o) {
                return ((T) o);
            }
        })
                .subscribeOn(sub)
                .observeOn(obs)
                .subscribe(subscriber);
        addSubscription(type, subscription);
        return subscription;
    }

    public <T> Subscription register(Class<T> type, Action1<T> next, Action1<Throwable> error) {
        Subscription subscription = toObservable(type)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);

        addSubscription(type, subscription);
        return subscription;
    }

    private void addSubscription(@NonNull Class<?> type, @NonNull Subscription subscription) {
        String key = type.getName();

        //  since our #register() is invoked in task which may occurred in multiple threads.... so we need it
        CompositeSubscription compositeSubscription = null;
        synchronized (mSubscriptionMap) {
            compositeSubscription = mSubscriptionMap.get(key);
            if (compositeSubscription == null) {
                compositeSubscription = new CompositeSubscription();
                mSubscriptionMap.put(key, compositeSubscription);
            }
        }
        compositeSubscription.add(subscription);           // which is synchronized method internal
    }

    public void unSubscribe(@NonNull Class<?> type, @NonNull Subscription subscription) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = type.getName();

        //  since this method is invoked in task which may occurred in multiple threads...
        CompositeSubscription compositeSubscription = null;
        synchronized (mSubscriptionMap) {
            compositeSubscription = mSubscriptionMap.get(key);
        }
        if (compositeSubscription != null) {
            compositeSubscription.remove(subscription);
            if (!compositeSubscription.hasSubscriptions()) {
                compositeSubscription.clear();              // clear and unSubscribe all existing subscription..
            }
        }
    }

    public void unSubscribe(@NonNull Class<?> type, @NonNull Collection<Subscription> subscriptions) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = type.getName();

        //  since this method is invoked in task which may occurred in multiple threads...
        CompositeSubscription compositeSubscription = null;
        synchronized (mSubscriptionMap) {
            compositeSubscription = mSubscriptionMap.get(key);
        }
        if (compositeSubscription != null) {
            for (Subscription subscription : subscriptions) {
                compositeSubscription.remove(subscription);    // internal synchronized method...
            }
            if (!compositeSubscription.hasSubscriptions()) {
                compositeSubscription.clear();              // clear and unSubscribe all existing subscription..
            }
        }
    }
}
