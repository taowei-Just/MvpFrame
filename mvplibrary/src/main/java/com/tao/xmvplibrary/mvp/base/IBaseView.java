package com.tao.xmvplibrary.mvp.base;


import com.tao.xmvplibrary.mvp.IPresenter;
import com.tao.xmvplibrary.mvp.IView;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface IBaseView< P extends IPresenter> extends  IView<P> {

    int getLayoutId();
    void initView();
    void initData();

}
