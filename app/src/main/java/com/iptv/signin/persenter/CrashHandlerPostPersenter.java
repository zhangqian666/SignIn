package com.iptv.signin.persenter;

import android.util.Log;

import com.iptv.signin.model.CrashHandlerPostModel;
import com.iptv.signin.view.CrashHandlerPostView;

/**
 * Created by ZhangQian on 2017/3/13 0013.
 */

public class CrashHandlerPostPersenter implements CrashHandlerPostModel.CrashHandlerPostModelListener {


    private final CrashHandlerPostModel model;
    private CrashHandlerPostView postView;
    public CrashHandlerPostPersenter(CrashHandlerPostView postView) {
        super();
        model = new CrashHandlerPostModel(this);
        this.postView = postView;
    }

    public void getErrorCrashHandlerResult(String stbid, String deviceInfo, String ex) {
        Log.e("TAG", "getErrorCrashHandlerResult: " );
        model.getErrorCrashHandlerResult(stbid,deviceInfo,ex);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(Object result) {
        postView.onCompleted(true);
    }

    @Override
    public void onError(Throwable e) {

    }
}
