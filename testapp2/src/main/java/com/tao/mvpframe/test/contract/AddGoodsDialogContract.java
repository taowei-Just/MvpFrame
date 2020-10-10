package com.tao.mvpframe.test.contract;

import android.content.Context;

import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;
import com.tao.mvplibrary.mvp.base.BasePresenter;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface AddGoodsDialogContract {

    interface AddGoodsDialogPresent<V extends IView> extends IPresenter<V> {
        void queryGoodsFromCode(String code);
        void subAddGoods(String code) throws Exception;
        void resetCommnuiction();
        void resetMotor();
        void updataGoodsByLocation(int tableLocation, int num);
        void dismiss();
    }

    interface AddGoodsDialogView<P extends BasePresenter> extends IView<P> {
        void onQueryGoodsData(Object goodsByCode);

        void onQueryGoodsFailed();

        void OnSubAddGoodsFaile(String s);

        Context getContext();

        void onAddGoodsAftre();

        void onAddGoodsFaile(boolean b, Object resultType);

        void onActiveOver(boolean b);

        void onAddGoodsSuccess(int tableLocation, int num);

        void onSetMove(boolean move);

        void onTimeOut(Object serialRsult);
    }


}
