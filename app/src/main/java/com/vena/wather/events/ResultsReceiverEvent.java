package com.vena.wather.events;

public interface ResultsReceiverEvent<T> {
    void onSuccess(T results);

    void onFailed(String message);
}
