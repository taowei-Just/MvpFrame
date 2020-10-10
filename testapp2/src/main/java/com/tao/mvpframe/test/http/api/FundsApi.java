package com.tao.mvpframe.test.http.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FundsApi {
    @GET("v7/finance/download/{code}")
    Call<ResponseBody> downloadFunds(@Path("code") String code, @Query("period1") String period1,
                                     @Query("period2") String period2, @Query("interval") String interval, @Query("events") String events); 
}
