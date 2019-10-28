package com.tao.mvpframe.presenter;

import com.tao.mvpframe.contract.MainActivtyContract;
import com.tao.mvpframe.modle.MainActivityModle;
import com.tao.mvplibrary.mvp.base.BasePresenter;

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
}
