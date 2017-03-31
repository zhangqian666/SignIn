package com.iptv.signin.model;

import com.iptv.signin.api.ApiManager;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.listener.BaseModelListener;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class UserFriendListModel extends BaseModel<BaseResult<List<LoginData>>>{
    public UserFriendListModel(BaseModelListener<BaseResult<List<LoginData>>> modelListener) {
        super();
        this.modelListener = modelListener;
    }
    public void getUserFriendsList(String userId){
        ApiManager.getUserFriendsList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<List<LoginData>>>() {
                    @Override
                    public void onCompleted() {
                        modelListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        modelListener.onError(e);
                    }

                    @Override
                    public void onNext(BaseResult<List<LoginData>> listBaseResult) {
                        modelListener.onNext(listBaseResult);
                    }
                });
    }
}
