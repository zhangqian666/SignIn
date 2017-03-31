package com.iptv.signin.persenter;

import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.model.SignUserInfoModel;
import com.iptv.signin.view.SignUserInfoView;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public class SignUserInfoPersenter implements SignUserInfoModel.SignUserInfoModelListener {
    private final SignUserInfoModel signUserInfoModel;
    SignUserInfoView signUserInfoView;

    public SignUserInfoPersenter(SignUserInfoView signUserInfoView) {
        super();
        this.signUserInfoView = signUserInfoView;
        signUserInfoModel = new SignUserInfoModel(this);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(BaseResult<LoginData> result) {
        signUserInfoView.onSuccess(result);
    }

    @Override
    public void onError(Throwable e) {
        signUserInfoView.onError(e.toString());
    }

    public void getSignUserInfo(String userId) {
        signUserInfoModel.getSignUserInfo(userId);
    }
}
