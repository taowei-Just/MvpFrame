package com.tao.mvpframe.test.http.base;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author yemao
 * @date 2017/4/9
 * @description 写自己的代码, 让别人说去吧!
 */

public abstract class BaseObserver<M, T extends BaseEntity<M>> implements Observer<T> {

    String TAG = getClass().getSimpleName();

    public BaseObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
//        Log.w(TAG, "onSubscribe: " );
//        System.err.println("onSubscribe "  +Thread.currentThread().toString());
        onRequestStart();
    }

    @Override
    public void onNext(T tBaseEntity) {
//        Log.w(TAG, "onNext: " );
//        System.err.println("onNext "  +Thread.currentThread().toString() );
        onRequestEnd();
        if (tBaseEntity.isCodeSuccess()) {
            try {
                onSuccees(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(tBaseEntity);
                onFailure(new Throwable("on respons code error "), tBaseEntity, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
//        Log.w(TAG, "onError: "+e.toString());//这里可以打印错误信息
//        System.err.println("onError"  );

        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, null, true);
            } else {
                onFailure(e, null, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
//        Log.w(TAG, "onComplete: " );
//        System.err.println("onComplete "  +Thread.currentThread().toString());
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws
     */
    protected abstract void onSuccees(T t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws
     */
    protected void onCodeError(T t) throws Exception {
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws
     */
    protected abstract void onFailure(Throwable e, T t, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
    }

    protected void onRequestEnd() {

    }


}
