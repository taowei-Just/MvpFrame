package com.tao.mvpframe.test.contract;


import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;

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
