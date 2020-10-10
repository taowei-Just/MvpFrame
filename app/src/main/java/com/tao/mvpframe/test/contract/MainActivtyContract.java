package com.tao.mvpframe.test.contract;


import com.tao.mvplibrary.mvp.IModle;
import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;

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
