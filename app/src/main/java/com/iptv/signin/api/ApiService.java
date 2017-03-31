package com.iptv.signin.api;


import com.iptv.signin.bean.BaseMovie;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.bean.SignInTime;

import java.util.List;

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

    @POST("sign/php/login.php")
    @FormUrlEncoded
    Observable<BaseResult<LoginData>> getLoginData(
            @Field("userAccount") String userAccount,
            @Field("userPassword") String userPassword
    );

    @POST("sign/php/updateqqid.php")
    @FormUrlEncoded
    Observable<BaseResult<String>> updateQQData(
            @Field("userId") String userId,
            @Field("userQQopenId") String userQQopenId,
            @Field("userQQAccessToken") String userQQAccessToken,
            @Field("userQQExpires") String userQQExpires,
            @Field("userName") String userName,
            @Field("userDesc") String userdesc,
            @Field("userHeadImage") String userheadImage
    );

    @POST("sign/php/userinfo.php")
    @FormUrlEncoded
    Observable<BaseResult<LoginData>> getSignUserInfo(
            @Field("userRongIMId") String userId
    );

    @POST("sign/php/selectfriends.php")
    @FormUrlEncoded
    Observable<BaseResult<List<LoginData>>> getUserFriendsList(
            @Field("userId") String userId
    );

    @POST("sign/php/friends.php")
    @FormUrlEncoded
    Observable<BaseResult<String>> addFriends(
            @Field("userId") String userId,
            @Field("fllowUserId") String fllowUserId
    );

    @POST("sign/php/savesignin.php")
    @FormUrlEncoded
    Observable<BaseResult<String>> setUserSignData(
            @Field("userId") String userId,
            @Field("signUserName") String signUserName,
            @Field("signData") String signData,
            @Field("signAdress") String signAdress,
            @Field("signDesc") String signDesc,
            @Field("signUserImage") String signUserImage,
            @Field("signId") String signId
    );

    @POST("sign/php/getsignin.php")
    @FormUrlEncoded
    Observable<BaseResult<List<SignInTime>>> getUserSignDataList(
            @Field("userId") String userId

    );

    @POST("sign/php/deletesignin.php")
    @FormUrlEncoded
    Observable<BaseResult<String>> deleteUserSignData(
            @Field("userId") String userId,
            @Field("signId") String signId


    );

    @POST("sign/php/getmovie.php")
    @FormUrlEncoded
    Observable<BaseResult<List<BaseMovie>>> getMovieList(
            @Field("userId") String userId
    );

}
