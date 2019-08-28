package com.tao.mvplibrary.mvp.base;


import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface IBaseView<P extends IPresenter> extends IView {

    int getLayoutId();

    void initView();

    void initData();

}
