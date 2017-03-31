package com.iptv.signin.persenter;

import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.model.UpdateQQModel;
import com.iptv.signin.view.UpdateQQView;

/**
 * Created by ZhangQian on 2017/3/25 0025.
 */

public class UpdateQQPersenter implements UpdateQQModel.UpdateQQModelListener {
    private final UpdateQQModel updateQQModel;
    private UpdateQQView updateQQView;

    public UpdateQQPersenter(UpdateQQView updateQQView) {
        super();
        this.updateQQView = updateQQView;
        updateQQModel = new UpdateQQModel(this);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(BaseResult<String> result) {
        updateQQView.updateSuccess(result);
    }

    @Override
    public void onError(Throwable e) {
        updateQQView.updateError(e.toString());
    }

    public void updateQQData(LoginData loginData) {
        updateQQModel.updateQQData(loginData);
    }
}
