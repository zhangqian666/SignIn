package com.iptv.signin.model;

import com.iptv.signin.api.ApiManager;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.listener.BaseModelListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangQian on 2017/3/29 0029.
 */

public class AddFriendsModel extends BaseModel<BaseResult<String>> {
    public AddFriendsModel(BaseModelListener<BaseResult<String>> modelListener) {
        this.modelListener = modelListener;
    }

    public void addFriends(String userId, String fllowUserId) {
        ApiManager.addFriends(userId, fllowUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<String>>() {
                    @Override
                    public void onCompleted() {
                        modelListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        modelListener.onError(e);
                    }

                    @Override
                    public void onNext(BaseResult<String> stringBaseResult) {
                        modelListener.onNext(stringBaseResult);
                    }
                });
    }
}
