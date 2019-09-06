package com.tao.mvplibrary.mvp.base;

import com.tao.mvplibrary.mvp.IModle;

public abstract class BaseModle<P extends BasePresenter> implements IModle<P> {
    P mPresenter;

    public BaseModle(P basePresenter) {
        this.mPresenter = basePresenter;
    }

    @Override
    public P getP() {
        return mPresenter;
    }


}
