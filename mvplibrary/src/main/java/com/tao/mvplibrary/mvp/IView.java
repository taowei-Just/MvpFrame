package com.tao.mvplibrary.mvp;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface IView <P extends IPresenter> {
    P getP();
    P getP(IView view);

}
