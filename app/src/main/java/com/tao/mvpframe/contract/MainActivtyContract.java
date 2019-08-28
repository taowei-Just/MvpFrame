package com.tao.mvpframe.contract;


import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface MainActivtyContract {
    
    interface MainActivtyPresenter < V extends IView> extends IPresenter<V > {
        
    }
    interface MainActivtyView < P extends IPresenter> extends IView<P >{
        
    }
    
    
}
