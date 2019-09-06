package com.tao.mvpframe.contract;


import com.tao.mvplibrary.mvp.IModle;
import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;
import com.tao.mvplibrary.mvp.base.IBaseView;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface MainActivtyContract {

    interface IMainActivtyPresenter<V extends IView> extends IPresenter<V> {

    }

    interface IMainActivtyView<P extends IPresenter> extends IBaseView<P> {
    }


    interface IMainactivityModle extends IModle {

        void testPost();

        void test();
        void testPostFile();
    }
}
