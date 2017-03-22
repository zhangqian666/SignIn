package com.iptv.signin.others;

import android.app.Application;
import android.content.Context;

import com.iptv.signin.utils.CrashHandler;

import org.litepal.LitePal;

import io.rong.imkit.RongIM;

/**
 * Created by ZhangQian on 2017/2/19 0019.
 */

public class SignInApplication extends Application {
    public static Context mContext;


    @Override

    public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
        //初始化数据库
        LitePal.initialize(getApplicationContext());
        //创建数据库
        LitePal.getDatabase();
        //初始化CrashHandler
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init();
        //融云聊天
        RongIM.init(this);
    }
}
