package com.iptv.signin.api;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ZhangQian on 2016/12/16 0016.
 */
public interface ApiService {


    @POST("log/error/save")
    @FormUrlEncoded
    Observable<Object> getErrorCrashHandlerResult(
            @Field("stbId") String stbId,
            @Field("deviceInfo") String deviceInfo,
            @Field("ex") String ex
    );
}
