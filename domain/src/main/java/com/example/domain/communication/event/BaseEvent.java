package com.example.domain.communication.event;

public class BaseEvent<T> {
    public T data;

    public BaseEvent() {
    }

    public BaseEvent(T data) {
        this.data = data;
    }
}
