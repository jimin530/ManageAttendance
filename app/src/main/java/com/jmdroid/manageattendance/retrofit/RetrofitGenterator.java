package com.jmdroid.manageattendance.retrofit;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tacademy on 2017-02-20.
 */
public class RetrofitGenterator {
    private static RetrofitGenterator ourInstance = new RetrofitGenterator();

    public static RetrofitGenterator getInstance() {
        return ourInstance;
    }

    private RetrofitGenterator() {
    }

    ////////////////////////////////////////////////////////////////////////Setting Retrofit2
    Retrofit retrofit;
    private static final String BASE_URL = "http://ec2-52-26-144-160.us-west-2.compute.amazonaws.com:3000";

    OkHttpClient client;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void launch_retrofit(Context context) {
        // 세션 처리를 위한 수정 ================================================================
        OkHttpClient.Builder clientTmp = new OkHttpClient.Builder();
        clientTmp.interceptors().add(new AddCookiesInterceptor());
        clientTmp.interceptors().add(new ReceivedCookiesInterceptor());
        clientTmp.connectTimeout(20, TimeUnit.SECONDS);
        clientTmp.readTimeout(30, TimeUnit.SECONDS);
        client = clientTmp.build();
        // ====================================================================================

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // 쿠키 적용
                // .client(client)
                .build();
    }

    RetrofitImpFactory retrofitImpFactory;

    public RetrofitImpFactory getRetrofitImpFactory() {
        if (retrofitImpFactory == null) {
            retrofitImpFactory = retrofit.create(RetrofitImpFactory.class);
        }
        return retrofitImpFactory;
    }
}

















