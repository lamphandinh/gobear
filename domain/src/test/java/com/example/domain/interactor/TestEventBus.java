package com.example.domain.interactor;

import com.example.domain.communication.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class TestEventBus implements EventBus {

    private List<Object> mPostObjectList = new ArrayList<>();

    @Override
    public void post(Object o) {
        mPostObjectList.add(o);
    }

    public List<Object> getPostedObjects() {
        return mPostObjectList;
    }

    public Object getLatestedEvent() {
        return mPostObjectList.get(mPostObjectList.size() - 1);
    }

    public void reset() {
        mPostObjectList.clear();
    }

    @Override
    public <T> Subscription register(Class<T> type, Subscriber<T> subscriber) {
        return null;
    }

    @Override
    public <T> Subscription registerOnSpecificThread(Class<T> type, Scheduler sub, Scheduler obs, Subscriber<T> subscriber) {
        return null;
    }

    @Override
    public <T> Subscription register(Class<T> type, Action1<T> next, Action1<Throwable> error) {
        return null;
    }

    @Override
    public void unSubscribe(Class<?> type, Subscription subscription) {

    }

    @Override
    public void unSubscribe(Class<?> type, Collection<Subscription> subscriptions) {

    }
}
