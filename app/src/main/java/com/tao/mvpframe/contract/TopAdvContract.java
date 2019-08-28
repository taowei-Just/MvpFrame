package com.tao.mvpframe.contract;


import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;
import com.tao.mvplibrary.mvp.base.IBaseView;

import java.util.HashMap;

/**
 * Created by Administrator on 2019-8-7.
 */

public interface TopAdvContract {
    
    interface ITopAdvPresenter< V extends IView>extends IPresenter<V> {
        
    }
     
    interface ITopAdvView<P extends IPresenter>  extends IBaseView {
        void notifyDataSetChanged(HashMap<Object, Object> dataMap);
    }
    
}
