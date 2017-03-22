package com.iptv.signin.utils;

import android.content.SharedPreferences;

import com.iptv.signin.bean.LoginData;

import static android.content.Context.MODE_PRIVATE;
import static com.iptv.signin.SignInApplication.mContext;

/**
 * Created by ZhangQian on 2017/3/21 0021.
 */

public class SpUtil {
    private static final String TAG = "SpUtil";

    public static void saveLoginData(LoginData loginData) {
        SharedPreferences.Editor edit = mContext.getSharedPreferences("LoginData", MODE_PRIVATE).edit();
        edit.putString("userName", loginData.getUserName());
        edit.putString("userPassword", loginData.getUserPassword());
        edit.putString("userId", loginData.getUserId());
        edit.putString("userToken", loginData.getUserToken());
        edit.putString("userHeadImage", loginData.getUserHeadImage());
        edit.putString("userDesc", loginData.getUserDesc());
        edit.apply();
    }

    public static LoginData getLoginData() {
        SharedPreferences sp = mContext.getSharedPreferences("LoginData", MODE_PRIVATE);
        LoginData loginData = new LoginData();
        loginData.setUserName(sp.getString("userName", "userName"));
        loginData.setUserPassword(sp.getString("userPassword", "userPassword"));
        loginData.setUserId(sp.getString("userId", "userId"));
        loginData.setUserToken(sp.getString("userToken", "userToken"));
        loginData.setUserHeadImage(sp.getString("userHeadImage", "userHeadImage"));
        loginData.setUserDesc(sp.getString("userDesc", "userDesc"));
        return loginData;
    }
}
