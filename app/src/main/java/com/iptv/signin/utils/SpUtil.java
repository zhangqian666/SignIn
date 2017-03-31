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

    public static void setLoginData(LoginData loginData) {
        SharedPreferences.Editor edit = mContext.getSharedPreferences("LoginData", MODE_PRIVATE).edit();
        edit.putString("userName", loginData.getUserName());
        edit.putString("userPassword", loginData.getUserPassword());
        edit.putString("userId", loginData.getUserId());
        edit.putString("userToken", loginData.getUserToken());
        edit.putString("userHeadImage", loginData.getUserHeadImage());
        edit.putString("userDesc", loginData.getUserDesc());
        edit.putString("userRongIMtoken", loginData.getUserRongIMToken());
        edit.putString("userRongIMId", loginData.getUserRongIMId());
        edit.putString("userQQopenId", loginData.getUserQQopenId());
        edit.apply();
    }

    public static LoginData getLoginData() {
        SharedPreferences sp = mContext.getSharedPreferences("LoginData", MODE_PRIVATE);
        LoginData loginData = new LoginData();
        loginData.setUserName(sp.getString("userName", ""));
        loginData.setUserPassword(sp.getString("userPassword", ""));
        loginData.setUserId(sp.getString("userId", ""));
        loginData.setUserToken(sp.getString("userToken", ""));
        loginData.setUserHeadImage(sp.getString("userHeadImage", ""));
        loginData.setUserDesc(sp.getString("userDesc", ""));
        loginData.setUserRongIMToken(sp.getString("userRongIMtoken", ""));
        loginData.setUserRongIMId(sp.getString("userRongIMId", ""));
        loginData.setUserQQopenId(sp.getString("userQQopenId", ""));
        loginData.setUserQQAccessToken(sp.getString("userQQAccessToken", ""));
        loginData.setUserQQExpires(sp.getString("userQQExpires", ""));
        return loginData;
    }

    public static void updataLoginData(LoginData tencentLoginResult) {
        LoginData loginData = getLoginData();
        SharedPreferences.Editor edit = mContext.getSharedPreferences("LoginData", MODE_PRIVATE).edit();
        edit.putString("userName", tencentLoginResult.getUserName());
        edit.putString("userPassword", loginData.getUserPassword());
        edit.putString("userId", loginData.getUserId());
        edit.putString("userToken", loginData.getUserToken());
        edit.putString("userHeadImage", tencentLoginResult.getUserHeadImage());
        edit.putString("userDesc", tencentLoginResult.getUserDesc());
        edit.putString("userRongIMtoken", loginData.getUserRongIMToken());
        edit.putString("userRongIMId", loginData.getUserRongIMId());
        edit.putString("userQQopenId", tencentLoginResult.getUserQQopenId());
        edit.putString("userQQAccessToken", tencentLoginResult.getUserQQAccessToken());
        edit.putString("userQQExpires", tencentLoginResult.getUserQQExpires());
        edit.apply();
    }



}
