package com.iptv.signin.listener;

/**
 * Created by ZhangQian on 2017/3/12 0012.
 */

public interface BaseModelListener<T> {

    void onCompleted();

    void onNext(T result);

    void onError(Throwable e);
}
