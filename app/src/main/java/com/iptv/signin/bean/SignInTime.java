package com.iptv.signin.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by ZhangQian on 2017/2/19 0019.
 */

public class SignInTime extends DataSupport{
    private int type;
    private String mChineseTime;
    private long mLongTime;
    private String content;
    private String title;
    private String user;
    private String localAddress;

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getmChineseTime() {
        return mChineseTime;
    }

    public void setmChineseTime(String mChineseTime) {
        this.mChineseTime = mChineseTime;
    }

    public long getmLongTime() {
        return mLongTime;
    }

    public void setmLongTime(long mLongTime) {
        this.mLongTime = mLongTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
