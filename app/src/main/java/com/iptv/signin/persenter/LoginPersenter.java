package com.iptv.signin.persenter;

import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.model.LoginModel;
import com.iptv.signin.view.LoginView;

/**
 * Created by ZhangQian on 2017/3/24 0024.
 */

public class LoginPersenter implements LoginModel.LoginDataModelListener {

    private final LoginModel loginModel;
    private LoginView loginView;

    public LoginPersenter(LoginView loginView) {
        super();
        this.loginView = loginView;
        loginModel = new LoginModel(this);
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onNext(BaseResult<LoginData> result) {
        if (result.getCode() == 200) {
            loginView.loginSuccess(true, result);
        } else {
            loginView.loginError("出错了");
        }
    }

    @Override
    public void onError(Throwable e) {
        loginView.loginError(e.toString());
    }

    public void getLoginData(String userAccount, String userPassword) {
        loginModel.getLoginData(userAccount, userPassword);
    }
}
