package com.tao.mvplibrary.mvp.base;

import android.os.Handler;
import android.os.Looper;


import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2019-8-7.
 */

public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    private String tag = getClass().getSimpleName();
    V view;
    public Handler handler = new Handler(Looper.getMainLooper());
    boolean deattachV = false;

    public boolean isDeattachV() {
        return deattachV;
    }

    public BasePresenter() {
        EventBus.getDefault().register(this);
    }

    private WeakReference<V> v;

    @Override
    public V getV() throws Exception{
//        LogUtil.e(tag ," getV "+v);
//        if(v!=null)
//        LogUtil.e(tag ," getV "+v.get());
        if (!isAttachedV())
            return null;
        return v.get();
    }

    public boolean isAttachedV() {

//        if (v != null) {
//            LogUtil.e(tag, "isAttachedV 1 " + v.getClass().getSimpleName());

//            if (v.get() != null)
//                LogUtil.e(tag, "isAttachedV 2 " + v.get().getClass().getSimpleName());
//        }
        return v != null && v.get() != null;
    }

    public void attachView(V view) {
        if (this.v != null)
            v.clear();
        this.view = view;
        this.v = new WeakReference<>(view);
        deattachV = false;
    }

    public void dettachView() {
        deattachV = true;
        EventBus.getDefault().unregister(this);
//        LogUtil.e(tag, "dettachView   ");
        view = null;
        if (null != v && null != v.get())
            v.clear();
        v = null;
    }


    @Subscribe
    public void sub(String str) {
    }
    
    public void  runOnUI(Runnable runnable){
        handler.post(runnable);
    }
}
