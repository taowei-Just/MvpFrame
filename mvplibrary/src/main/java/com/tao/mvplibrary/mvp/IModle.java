package com.tao.mvplibrary.mvp;

import com.tao.mvplibrary.mvp.base.BasePresenter;

public interface IModle<P extends BasePresenter> {

    P getP();
}
