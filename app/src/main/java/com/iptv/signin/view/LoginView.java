package com.iptv.signin.view;

import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;

/**
 * Created by ZhangQian on 2017/3/24 0024.
 */

public interface LoginView {
    void loginSuccess(boolean isSuccess, BaseResult<LoginData> mResult);

    void loginError(String error);
}
