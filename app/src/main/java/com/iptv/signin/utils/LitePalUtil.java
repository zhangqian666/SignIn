package com.iptv.signin.utils;

import com.iptv.signin.bean.LoginData;

import org.litepal.crud.DataSupport;

/**
 * Created by ZhangQian on 2017/3/21 0021.
 */

public class LitePalUtil {
    public static void saveLoginData(LoginData loginData) {
        if (DataSupport.findFirst(LoginData.class) != null) {
            loginData.save();
        } else {
            loginData.update(0);
        }
    }

    public static LoginData getLoginData() {
        return DataSupport.findFirst(LoginData.class);
    }
}
