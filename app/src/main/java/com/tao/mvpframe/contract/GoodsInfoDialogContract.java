package com.tao.mvpframe.contract;

 
import com.tao.xmvplibrary.mvp.IPresenter;
import com.tao.xmvplibrary.mvp.IView;
import com.tao.xmvplibrary.mvp.base.BasePresenter;


import java.util.List;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface GoodsInfoDialogContract {

    interface IGoodsInfoDialogPresent<V extends IView> extends IPresenter<V> {

        void updataLocationNum(Object tableLocation);
    }

    interface IGoodsInfoDialogView<P extends BasePresenter, G extends  Object> extends IView<P> {
        void onGoodsList(List<G> goodsDataList);
    }


}
