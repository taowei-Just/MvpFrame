package com.tao.mvpframe.presenter;

import android.util.Log;

import com.tao.mvpframe.contract.MainActivtyContract;
import com.tao.mvpframe.modle.MainActivityModle;
import com.tao.xmvplibrary.mvp.base.BasePresenter;

public class MainActivityPresent extends BasePresenter<MainActivtyContract.IMainActivtyView, MainActivtyContract.IMainactivityModle> implements MainActivtyContract.IMainActivtyPresenter<MainActivtyContract.IMainActivtyView> {

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(tag, " back thread " + Thread.currentThread());
                runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(tag, "ui thread" + Thread.currentThread());

                    }
                });
            }
        }).start();

    }

    @Override
    public void test001() {
        getM().test001();
    }
}
