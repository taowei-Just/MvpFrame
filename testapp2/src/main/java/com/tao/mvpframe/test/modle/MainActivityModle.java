package com.tao.mvpframe.test.modle;

import android.util.Log;

import com.tao.mvpbaselibrary.retrofitrx.RetrofitFactory;
import com.tao.mvpframe.test.contract.MainActivtyContract;
import com.tao.mvpframe.test.http.api.MyApi;
import com.tao.mvpframe.test.http.base.BaseObserver;
import com.tao.mvpframe.test.http.base.MyBaseEntity;
import com.tao.mvpframe.test.http.bean.MockEntity;
import com.tao.mvpframe.test.http.bean.PostFileTestEntity;
import com.tao.mvpframe.test.http.bean.PostTestEntity;
import com.tao.mvpframe.test.http.constant.ConstantUrl;
import com.tao.mvplibrary.mvp.base.BaseModle;
import com.tao.mvplibrary.mvp.base.BasePresenter;
import com.tao.mvplibrary.utils.RxUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivityModle extends BaseModle implements MainActivtyContract.IMainactivityModle {

    public MainActivityModle(BasePresenter basePresenter) {
        super(basePresenter);
    }

    @Override
    public void testPost() {
        try {
           RxUtils.toSubscribe(RetrofitFactory.getInstence(ConstantUrl.baseUrl).rxGsonAPI(MyApi.class).postTest("test"),
                    new BaseObserver<PostTestEntity, MyBaseEntity<PostTestEntity>>() {
                        protected void onSuccees(MyBaseEntity<PostTestEntity> stringBaseEntity) throws Exception {
                            Log.e("tag", " onSuccees " + stringBaseEntity.toString());
                        }

                        @Override
                        protected void onFailure(Throwable e, MyBaseEntity<PostTestEntity> entity, boolean isNetWorkError) throws Exception {
                            Log.e("tag", "onFailure " + e.toString());
                        }
                    },getP().getLifecycle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void test() {
        try {
          RxUtils.toSubscribe(RetrofitFactory.getInstence(ConstantUrl.baseUrl).rxGsonAPI(MyApi.class).getMock(), new BaseObserver<MockEntity, MyBaseEntity<MockEntity>>() {
              @Override
              protected void onSuccees(MyBaseEntity<MockEntity> stringBaseEntity) throws Exception {
                  Log.e("tag", " onSuccees " + stringBaseEntity.toString());
              }

              @Override
              protected void onFailure(Throwable e, MyBaseEntity<MockEntity> stringBaseEntity, boolean isNetWorkError) throws Exception {
                  Log.e("tag", "onFailure " + e.toString());
              }
          },getP().getLifecycle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testPostFile() {
        Map<String, RequestBody> multMap = new HashMap<>();
        File file = new File("/sdcard/test");
        multMap.put("files\";filename=\"" + file.getName(), MultipartBody.create(MultipartBody.FORM, file));
        try {
            RxUtils.toSubscribe(RetrofitFactory.getInstence("http://tobacco.sun-hyt.com:8078/").rxGsonAPI(MyApi.class).pushFilesEntity(multMap),
                    new BaseObserver<List<String>, PostFileTestEntity<List<String>>>() {
                        @Override
                        protected void onSuccees(PostFileTestEntity<List<String>> stringPostFileTestEntity) throws Exception {
                            System.err.println("onSuccees" + stringPostFileTestEntity.toString());
                        }

                        @Override
                        protected void onFailure(Throwable e, PostFileTestEntity<List<String>> stringPostFileTestEntity, boolean isNetWorkError) throws Exception {
                            System.err.println(e.toString());
                            if (null != stringPostFileTestEntity)
                                System.err.println("onFailure" + stringPostFileTestEntity.toString());
                        }
                    }
                    ,getP().getLifecycle()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void test001() {

//        try {
//            RxUtils.toSubscribe(RetrofitFactory.getInstence("http://tobacco.sun-hyt.com:8078/").);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
