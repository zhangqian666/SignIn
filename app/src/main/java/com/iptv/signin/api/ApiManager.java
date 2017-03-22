package com.iptv.signin.api;

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
            hotelUrl = "http://10.0.2.210";//http://10.0.2.210
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


    public static Observable<Object> getErrorCrashHandlerResult(String stbid, String deviceInfo,String ex) {
        return apiService.getErrorCrashHandlerResult(stbid,deviceInfo,ex);
    }


}
