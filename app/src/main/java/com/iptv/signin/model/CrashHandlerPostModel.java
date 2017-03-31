package com.iptv.signin.model;

import android.util.Log;

import com.iptv.signin.listener.BaseModelListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZhangQian on 2017/3/13 0013.
 */

public class CrashHandlerPostModel extends BaseModel<Object> {
    private static final String TAG = "CrashHandlerPostModel";
    private CrashHandlerPostModelListener modelListener;
    private OkHttpClient client;

    public CrashHandlerPostModel(CrashHandlerPostModelListener modelListener) {
        this.modelListener = modelListener;
    }

    public void getErrorCrashHandlerResult(String stbid, String deviceInfo, String ex) {
        Log.e(TAG, "getErrorCrashHandlerResult: ");
        FormBody.Builder params = new FormBody.Builder();
        params.add("stbId", "0010049900511A7000A700226D11B9DE");
        params.add("deviceInfo", "deviceInfo");
        params.add("ex", ex);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://10.0.2.210/log/error/save")
                .post(params.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call arg0, Response response) throws IOException {
                //   响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                Log.e(TAG, "onResponse: " + response.message() + "---" + response.body().string());
                modelListener.onNext(response.message());
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                //   响应失败
                Log.e(TAG, "onFailure: ");
            }

        });
    }

    public interface CrashHandlerPostModelListener extends BaseModelListener<Object> {

    }
}
