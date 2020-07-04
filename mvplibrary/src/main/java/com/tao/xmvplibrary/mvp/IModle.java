package com.tao.xmvplibrary.mvp;

import com.tao.xmvplibrary.mvp.base.BasePresenter;

public interface IModle<P extends BasePresenter> {

    P getP();
    void deattach();
}
