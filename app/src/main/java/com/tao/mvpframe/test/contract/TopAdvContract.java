package com.tao.mvpframe.test.contract;


import com.tao.mvplibrary.mvp.IBaseView;
import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;

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
