package com.tao.mvpframe.test.presenter;

 

import android.os.Handler;
import android.util.Log;

import com.tao.logger.log.Logger;
import com.tao.mvpbaselibrary.mvp.base.BaseObserver;
import com.tao.mvpframe.test.contract.MainActivtyContract;
import com.tao.mvpframe.test.modle.MainActivityModle;
import com.tao.mvplibrary.mvp.base.BasePresenter;
import com.tao.mvplibrary.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivityPresent extends BasePresenter<MainActivtyContract.IMainActivtyView, MainActivtyContract.IMainactivityModle>
        implements MainActivtyContract.IMainActivtyPresenter<MainActivtyContract.IMainActivtyView> {

    public void testPost() {
        getM().testPost();
    }

    public void test() {
        getM().test();
    }

    public void testPostFile() {
        getM().testPostFile();
    }

    @Override
    public MainActivtyContract.IMainactivityModle creatM() {
        return new MainActivityModle(this);
    }

    public void switchUi() {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                Log.e(tag,"Runnable "+" switchUi " +Thread.currentThread().getName());
            }
        });
    }

    @Override
    public void test001() {
        getM().test001();
    }

    Handler handler =new Handler();
    public void test002() {
        Log.e(tag ,"test002  "  +Thread.currentThread().getName());

        RxUtils.toSubscribe(Observable.interval(0, 1, TimeUnit.SECONDS), new BaseObserver<Long>() {

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                Log.e(tag,"onSubscribe:  "  +Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(tag,"onError:" );
            }

            @Override
            public void onComplete() {
                super.onComplete();
                Log.e(tag,"onComplete:" );
            }

            @Override
            protected void accept(Long o) {
              Log.e(tag,"accept:"+o+"  " +Thread.currentThread().getName());
              goUi();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(tag,"handler Runnable accept:"+o+"  " +Thread.currentThread().getName());
                     
                    }
                });

                runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(tag,"runOnUI accept:"+"  " +Thread.currentThread().getName());
                    }
                });
            }
        } ,getLifecycle() , false,false);
        
    }

    private void goUi() {
        runOnUI(new Runnable() {
            @Override
            public void run() {
                Log.e(tag ," goUi +runOnUI " +Thread.currentThread().getName())     ;
            }
        });
        
    }
}
