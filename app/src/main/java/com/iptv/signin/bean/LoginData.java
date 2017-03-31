package com.iptv.signin.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by ZhangQian on 2017/3/21 0021.
 */

public class LoginData extends DataSupport{
    private String userName;
    private String userPassword;
    private String userId;
    private String userToken;
    private String userRongIMToken;
    private String userRongIMId;
    private String userHeadImage;
    private String userDesc;
    private String userQQopenId;
    private String userQQAccessToken;
    private String userQQExpires;

    public String getUserQQAccessToken() {
        return userQQAccessToken;
    }

    public void setUserQQAccessToken(String userQQAccessToken) {
        this.userQQAccessToken = userQQAccessToken;
    }

    public String getUserQQExpires() {
        return userQQExpires;
    }

    public void setUserQQExpires(String userQQExpires) {
        this.userQQExpires = userQQExpires;
    }

    public String getUserQQopenId() {
        return userQQopenId;
    }

    public void setUserQQopenId(String userQQopenId) {
        this.userQQopenId = userQQopenId;
    }

    public String getUserRongIMId() {
        return userRongIMId;
    }

    public void setUserRongIMId(String userRongIMId) {
        this.userRongIMId = userRongIMId;
    }

    public String getUserRongIMToken() {
        return userRongIMToken;
    }

    public void setUserRongIMToken(String userRongIMToken) {
        this.userRongIMToken = userRongIMToken;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userId='" + userId + '\'' +
                ", userToken='" + userToken + '\'' +
                ", userRongIMToken='" + userRongIMToken + '\'' +
                ", userRongIMId='" + userRongIMId + '\'' +
                ", userHeadImage='" + userHeadImage + '\'' +
                ", userDesc='" + userDesc + '\'' +
                ", userQQopenId='" + userQQopenId + '\'' +
                '}';
    }
}
