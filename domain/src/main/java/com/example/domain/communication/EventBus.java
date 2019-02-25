package com.example.domain.communication;

import java.util.Collection;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public interface EventBus {
    void post(Object o);

    <T> Subscription register(Class<T> type, Subscriber<T> subscriber);

    <T> Subscription registerOnSpecificThread(Class<T> type, Scheduler sub, Scheduler obs, Subscriber<T> subscriber);

    <T> Subscription register(Class<T> type, Action1<T> next, Action1<Throwable> error);

    void unSubscribe(Class<?> type, Subscription subscription);

    void unSubscribe(Class<?> type, Collection<Subscription> subscriptions);
}
