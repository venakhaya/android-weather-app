package com.vena.wather.events;

/**
 * Created by khaya on 2018/04/20.
 */

public interface FailureEvent {
    void onFailed(String message);
}
