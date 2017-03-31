package com.iptv.signin.model;

import com.iptv.signin.api.ApiManager;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.listener.BaseModelListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangQian on 2017/3/24 0024.
 */

public class LoginModel {
    private LoginDataModelListener loginDataModelListener;

    public LoginModel(LoginDataModelListener loginDataModelListener) {
        super();
        this.loginDataModelListener = loginDataModelListener;
    }

    public void getLoginData(String userAccount, String userPassword) {
        ApiManager.getLoginData(userAccount, userPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<LoginData>>() {
                    @Override
                    public void onCompleted() {
                        loginDataModelListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginDataModelListener.onError(e);
                    }

                    @Override
                    public void onNext(BaseResult<LoginData> loginData) {
                        loginDataModelListener.onNext(loginData);
                    }
                });
    }

    public interface LoginDataModelListener extends BaseModelListener<BaseResult<LoginData>> {

    }
}
