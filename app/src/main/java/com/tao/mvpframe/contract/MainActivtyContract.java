package com.tao.mvpframe.contract;


import com.tao.xmvplibrary.mvp.IModle;
import com.tao.xmvplibrary.mvp.IPresenter;
import com.tao.xmvplibrary.mvp.IView;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface MainActivtyContract {

    interface IMainActivtyPresenter<V extends IView> extends IPresenter<V> {
        void test001();
    }

    interface IMainActivtyView<P extends IPresenter> extends IView<P> {
    }


    interface IMainactivityModle extends IModle {

        void testPost();
        void test();
        void testPostFile();
        void test001();
    }
}
