package com.iptv.signin.view;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public interface BaseView<T> {
    void onError(String ex);
    void onSuccess(T result);
}
