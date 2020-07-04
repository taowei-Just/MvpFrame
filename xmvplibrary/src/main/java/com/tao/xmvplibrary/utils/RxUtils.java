package com.tao.xmvplibrary.utils;


import androidx.lifecycle.Lifecycle;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
 
    /**
     * 订阅
     *
     * @param observable    被观察者
     * @param observer      观察者
     * @param lifecycle     使用生命周期自动接绑  可为null
     * @param observeOnMain
     * @param <T>
     */


    public static <T> void toSubscribe(Observable<T> observable, Observer<? super T> observer, Lifecycle lifecycle, boolean observeOnMain) {

        if (lifecycle == null) {
            getEntityObservable(observable, observeOnMain).subscribe(observer);
        } else {
            ((ObservableSubscribeProxy) observable.compose(getComposer(observeOnMain)).as(bindLifecycle(lifecycle))).subscribe(observer);
        }
    }

    /**
     *  绑定 生命周期 默认观察者为main线程
     * @param observable
     * @param resultObserver
     * @param lifecycle
     * @param <T>
     */
    public static <T> void toSubscribe(Observable<T> observable, Observer<T> resultObserver, Lifecycle lifecycle) {
        toSubscribe(observable, resultObserver, lifecycle, true);
    }

    /**
     *  不绑定 生命周期 默认观察者为main线程
     * @param observable
     * @param resultObserver
     * @param <T>
     */
    public static <T> void toSubscribe(Observable<T> observable, Observer<T> resultObserver) {
        toSubscribe(observable, resultObserver, true);
    }

    /**
     *  不绑定 生命周期  自由设置观察者线程
     * @param observable
     * @param resultObserver
     * @param <T>
     */
    public static <T> void toSubscribe(Observable<T> observable, Observer<T> resultObserver, boolean obserMain) {
        toSubscribe(observable, resultObserver, null, obserMain);
    }
    
    
    /**
     * 绑定到lifecycle
     *
     * @param lifecycle
     * @param <T>
     * @return
     */
    public static <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle lifecycle) {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle, Lifecycle.Event.ON_DESTROY));
    }

    /**
     * 线程调度器
     *
     * @param observeOnMain
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> getComposer(final boolean observeOnMain) {
        return new ObservableTransformer<T, T>() {
            public ObservableSource<T> apply(Observable<T> upstream) {
                if (observeOnMain)
                    return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                else
                    return upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            }
        };
    }

    /**
     * 是否切换到UI线程
     *
     * @param observable
     * @param observeOnMain
     * @param <T>
     * @return
     */
    private static <T> Observable<T> getEntityObservable(Observable<T> observable, boolean observeOnMain) {
        Observable<T> tObservable = observable.subscribeOn(Schedulers.io());
        return observeOnMain ? tObservable.observeOn(AndroidSchedulers.mainThread()) : tObservable;
    }

}
