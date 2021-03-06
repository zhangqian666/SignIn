package com.iptv.signin;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.iptv.signin.ui.rongim.mlistener.ReceiveMessageListener;
import com.iptv.signin.utils.CrashHandler;

import org.litepal.LitePal;

import io.rong.imkit.RongIM;

import static android.content.ContentValues.TAG;

/**
 * Created by ZhangQian on 2017/2/19 0019.
 */

public class SignInApplication extends Application {
    public static Context mContext;


    @Override

    public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
        Log.e(TAG, "onCreate: ");
        //初始化数据库
        LitePal.initialize(getApplicationContext());
        //创建数据库
        LitePal.getDatabase();
        //初始化CrashHandler
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init();
        //融云聊天
        RongIM.init(this);
        RongIM.setOnReceiveMessageListener(new ReceiveMessageListener());
    }
}
