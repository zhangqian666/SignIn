package com.iptv.signin.persenter;

import com.iptv.signin.bean.SignInTime;
import com.iptv.signin.model.SignInModel;
import com.iptv.signin.view.SignInView;

import java.util.List;

/**
 * Created by ZhangQian on 2017/3/12 0012.
 */

public class SignInPersenter implements SignInModel.SignInModelListener {

    private final SignInModel signInModel;
    private SignInView signInView;

    public SignInPersenter(SignInView signInView) {
        super();
        signInModel = new SignInModel(this);
        this.signInView = signInView;
    }

    /**
     * 获取全部信息
     *
     * @param message
     */
    public void getSignInMessage(String message) {
        signInModel.getSignInMessage(message);
    }

    /**
     * 保存单个信息
     *
     * @param signInTime
     */
    public void saveSignInMessage(SignInTime signInTime) {
        signInModel.saveSignInMessage(signInTime);
    }

    /**
     * 按条件删除数据
     * @param signInTime
     * @param time
     */
    public void deleteSignInMessage(SignInTime signInTime, String time) {
        signInModel.deleteSignInMessage(signInTime, time);
    }

    @Override
    public void setResult(SignInTime result) {

    }

    @Override
    public void setResulList(List<SignInTime> resultList) {
        signInView.setAllSignInMessage(resultList);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(SignInTime result) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void setSaveResult(boolean isSuccess) {
        signInView.setSaveResult(true);
    }
}
