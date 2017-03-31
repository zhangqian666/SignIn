package com.iptv.signin.api;

import com.iptv.signin.bean.BaseMovie;
import com.iptv.signin.bean.BaseResult;
import com.iptv.signin.bean.LoginData;
import com.iptv.signin.bean.SignInTime;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/*
 */
public class ApiManager {

    private static ApiService apiService;
    private static boolean isFormalUrl = true;
    private static String hotelUrl;

    static {
        if (isFormalUrl) {
            hotelUrl = "http://47.93.35.239";//http://10.0.2.210
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(hotelUrl).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    public static Observable<Object> getErrorCrashHandlerResult(String stbid, String deviceInfo, String ex) {
        return apiService.getErrorCrashHandlerResult(stbid, deviceInfo, ex);
    }

    public static Observable<BaseResult<LoginData>> getLoginData(String userAccount, String userPassword) {
        return apiService.getLoginData(userAccount, userPassword);
    }

    public static Observable<BaseResult<String>> updateQQData(LoginData loginData) {
        return apiService.updateQQData(
                loginData.getUserId(),
                loginData.getUserQQopenId(),
                loginData.getUserQQAccessToken(),
                loginData.getUserQQExpires(),
                loginData.getUserName(),
                loginData.getUserDesc(),
                loginData.getUserHeadImage());
    }

    public static Observable<BaseResult<LoginData>> getSignUserInfo(String userId) {
        return apiService.getSignUserInfo(userId);
    }

    public static Observable<BaseResult<List<LoginData>>> getUserFriendsList(String userId) {
        return apiService.getUserFriendsList(userId);
    }

    public static Observable<BaseResult<String>> addFriends(String userId, String fllowUserId) {
        return apiService.addFriends(userId, fllowUserId);
    }

    public static Observable<BaseResult<String>> setUserSignData(String userId, String signUserName, String signData, String signAdress, String signDesc, String signUserImage, String signId) {
        return apiService.setUserSignData(userId, signUserName, signData, signAdress, signDesc, signUserImage, signId);
    }

    public static Observable<BaseResult<List<SignInTime>>> getUserSignDataList(String userId) {
        return apiService.getUserSignDataList(userId);
    }

    public static Observable<BaseResult<String>> deleteUserSignData(String userId, String signId) {
        return apiService.deleteUserSignData(userId, signId);
    }

    public static Observable<BaseResult<List<BaseMovie>>> getMovieList(String userId) {
        return apiService.getMovieList(userId);
    }
}
