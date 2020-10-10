package com.tao.mvpframe.test.http.bean;

import com.tao.mvpbaselibrary.retrofitrx.RetrofitFactory;
import com.tao.mvpframe.test.http.api.MyApi;
import com.tao.mvpframe.test.http.base.BaseObserver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class PostTestEntity {
    String str;

    @Override
    public String toString() {
        return "PostTestEntity{" +
                "str='" + str + '\'' +
                '}';
    }


    public static void main(String[] args) throws Exception {

        Map<String, RequestBody> multMap = new HashMap<>();
        File file = new File( "/sdcard/test");
        multMap.put("files\";filename=\"" + file.getName(), MultipartBody.create(MultipartBody.FORM, file));

        Observable<PostFileTestEntity<List<String>>> postFileTestEntityObservable = RetrofitFactory.getInstence("http://tobacco.sun-hyt.com:8078/").rxGsonAPI(MyApi.class).pushFilesEntity(multMap);
        postFileTestEntityObservable
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<List<String>, PostFileTestEntity<List<String>>>() {
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
                });


        RetrofitFactory.getInstence("http://tobacco.sun-hyt.com:8078/").rxGsonAPI(MyApi.class).pushFiles(multMap)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.err.println("onSubscribe");

                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            System.err.println("onNext" + value.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("onError " + e.toString());

                    }

                    @Override
                    public void onComplete() {
                        System.err.println("onComplete ");

                    }
                });

    }
}
