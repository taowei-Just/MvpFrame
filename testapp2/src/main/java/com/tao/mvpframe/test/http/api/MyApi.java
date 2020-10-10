package com.tao.mvpframe.test.http.api;

import com.tao.mvpframe.test.http.base.MyBaseEntity;
import com.tao.mvpframe.test.http.bean.MockEntity;
import com.tao.mvpframe.test.http.bean.PostFileTestEntity;
import com.tao.mvpframe.test.http.bean.PostTestEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface MyApi {
    
    @GET("mock")
    Observable<MyBaseEntity<MockEntity>> getMock();
    @FormUrlEncoded
    @POST("post/test")
    Observable<MyBaseEntity<PostTestEntity>> postTest(@Field("test") String test);
    @POST("")
    Observable  post(@Body RequestBody requestBody);
    @Multipart
    @POST("log")
    Observable <ResponseBody>pushFiles(@PartMap Map<String, RequestBody> partMap);
    @Multipart
    @POST("log")
    Observable <PostFileTestEntity<List<String>>>pushFilesEntity(@PartMap Map<String, RequestBody> partMap);
    
        
    
    
}
