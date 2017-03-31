package com.iptv.signin.model;

import com.iptv.signin.api.ApiManager;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.listener.BaseModelListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class SignUserInfoModel {
    SignUserInfoModelListener modelListener;
    public SignUserInfoModel(SignUserInfoModelListener modelListener) {
        super();
        this.modelListener = modelListener;
    }
    public void getSignUserInfo(String userId) {
        ApiManager.getSignUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<LoginData>>() {
                    @Override
                    public void onCompleted() {
                        modelListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        modelListener.onError(e);
                    }

                    @Override
                    public void onNext(BaseResult<LoginData> loginDataBaseResult) {
                        modelListener.onNext(loginDataBaseResult);
                    }
                });
    }

    public interface SignUserInfoModelListener extends BaseModelListener<BaseResult<LoginData>>{}
}
