package com.iptv.signin.model;

import com.iptv.signin.api.ApiManager;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.listener.BaseModelListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangQian on 2017/3/25 0025.
 */

public class UpdateQQModel {
    private UpdateQQModelListener qqModelListener;

    public UpdateQQModel(UpdateQQModelListener qqModelListener) {
        super();
        this.qqModelListener = qqModelListener;
    }

    public void updateQQData(LoginData loginData) {
        ApiManager.updateQQData(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<String>>() {
                    @Override
                    public void onCompleted() {
                        qqModelListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        qqModelListener.onError(e);
                    }

                    @Override
                    public void onNext(BaseResult<String> stringBaseResult) {
                        qqModelListener.onNext(stringBaseResult);
                    }
                });

    }

    public interface UpdateQQModelListener extends BaseModelListener<BaseResult<String>> {

    }
}
