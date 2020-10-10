package com.tao.mvpframe.test.contract;


import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;
import com.tao.mvplibrary.mvp.base.BasePresenter;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface PayDialogContract {
    
    interface  PayDialogPresent < V extends IView> extends IPresenter<V> {
        void startTime(int time);
        void stopTime();
        void startCardRead();
        void print();
        void receipt();
        void submitEmail();

        void startOutgoods(Object shoopdata);
    }
    
    interface  PayDialogView <P extends BasePresenter> extends IView<P>{
        void onTimeText(long str);
        void onCradRead(String id);
        void onPrint();
        void onReceipt();
        void onEmailSubmited();

        void onSerialBusy();

        void onOutGoodsSuccess();
        
        void onOutGoodsFaile(boolean out, Object str);
    }
 
    
}
