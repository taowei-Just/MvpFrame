package com.tao.mvpframe.contract;


import com.tao.xmvplibrary.mvp.IPresenter;
import com.tao.xmvplibrary.mvp.IView;

import java.util.List;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface ShopCarContract {

    interface ShopCarPresent<V extends IView> extends IPresenter<V> {  }

    interface ShopCarView extends IView {
        void onAddGoods(List<Object> goodsList);
    }


}
