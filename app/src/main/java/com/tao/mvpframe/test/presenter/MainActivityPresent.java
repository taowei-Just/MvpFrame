package com.tao.mvpframe.test.presenter;

 

import com.tao.mvpbaselibrary.mvp.base.BaseObserver;
import com.tao.mvpframe.test.contract.MainActivtyContract;
import com.tao.mvpframe.test.modle.MainActivityModle;
import com.tao.mvplibrary.mvp.base.BasePresenter;
import com.tao.mvplibrary.utils.RxUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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
                
            }
        });
        RxUtils.toSubscribe(Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

            }
        }), new BaseObserver() {
           

            @Override
            protected void accept(Object o) {
                
            }
        }, getLifecycle());

    }

    @Override
    public void test001() {
        getM().test001();
    }
}
