package com.iptv.signin.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by ZhangQian on 2017/2/19 0019.
 */

public class SignInTime extends DataSupport {
    String userId;
    String signUserName;
    String signData;
    String signAdress;
    String signDesc;
    String signUserImage;
    String signId;

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getSignUserName() {
        return signUserName;
    }

    public void setSignUserName(String signUserName) {
        this.signUserName = signUserName;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getSignAdress() {
        return signAdress;
    }

    public void setSignAdress(String signAdress) {
        this.signAdress = signAdress;
    }

    public String getSignDesc() {
        return signDesc;
    }

    public void setSignDesc(String signDesc) {
        this.signDesc = signDesc;
    }

    public String getSignUserImage() {
        return signUserImage;
    }

    public void setSignUserImage(String signUserImage) {
        this.signUserImage = signUserImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
