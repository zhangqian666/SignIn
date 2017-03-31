package com.iptv.signin.view;

import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;

/**
 * Created by ZhangQian on 2017/3/26 0026.
 */

public interface SignUserInfoView {
    void onSuccess(BaseResult<LoginData> result);
    void onError(String ex);
}
