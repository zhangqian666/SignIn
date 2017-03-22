package com.iptv.signin.model;

import java.util.List;

/**
 * Created by ZhangQian on 2017/3/12 0012.
 */

public interface BaseModelListener<T> {

    void setResult(T result);

    void setResulList(List<T> resultList);

    void onCompleted();

    void onNext(T result);

    void onError(Throwable e);
}
