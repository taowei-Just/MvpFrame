package com.tao.mvpframe.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2020-4-7.
 */

public class RxHttpUtils {

    public static String baseUrl = "";

    static RxHttpUtils rxHttpUtils;
    static Map<Class, Retrofit> apiServerMap = new HashMap();
    static Map<String, Retrofit> retrofitMap = new HashMap();
    private OkHttpClient okHttpClient;

    public static RxHttpUtils getInstance() {
        synchronized (RxHttpUtils.class) {
            if (null == rxHttpUtils)
                rxHttpUtils = new RxHttpUtils();
        }
        return rxHttpUtils;
    }

    public RxHttpUtils() {
        initokhttp();
        if (retrofitMap.get(baseUrl) == null) {
            retrofitMap.put(baseUrl, initRetrofit(baseUrl));
        }
    }

    private void initokhttp() {
        okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(chain.request());
                    }
                }).build();
    }

    private Retrofit initRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();
        return retrofit;
    }

    public <T> T getApiService(Class<T> tClass) {
        return getApiService(baseUrl, tClass);
    }

    public synchronized <T> T getApiService(String baseUrl, Class<T> tClass) {
        if (retrofitMap.get(baseUrl) == null) {
            retrofitMap.put(baseUrl, initRetrofit(baseUrl));
        }
        return retrofitMap.get(baseUrl).create(tClass);
    }

}
