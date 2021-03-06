package com.tao.mvplibrary.mvp.base;

import android.arch.lifecycle.Lifecycle;


import com.tao.mvplibrary.mvp.IModle;
import com.tao.mvplibrary.mvp.IPresenter;
import com.tao.mvplibrary.mvp.IView;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2019-8-7.
 */

public abstract class BasePresenter<V extends IView, M extends IModle> implements IPresenter<V> {
    public String tag = getClass().getSimpleName();
    private boolean deattachV = false;
    private WeakReference<V> v;
    public M mModle;
    WeakReference<Lifecycle> lifecycle;

    public boolean isDeattachV() {
        return deattachV;
    }

    public BasePresenter() {
        EventBus.getDefault().register(this);
        setM(creatM());
    }

    public Lifecycle getLifecycle() {
        Lifecycle lifecycle = this.lifecycle.get();
        if (null == lifecycle && v != null && v.get() != null && (v.get() instanceof Lifecycle)) {
            lifecycle = (Lifecycle) v.get();
        }
        return lifecycle;
    }

    public void setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = new WeakReference<Lifecycle>(lifecycle);
    }

    public M creatM() {
        return null;
    }

    @Override
    public V getV() throws Exception {
        if (!isAttachedV())
            throw new Exception("v is null or v un attached");

        return v.get();
    }

    public boolean isAttachedV() {
        return v != null && v.get() != null;
    }

    public void setM(M modle) {
        mModle = modle;
    }

    final public M getM() {
        return mModle;
    }

    public void attachView(V view) {
        if (this.v != null)
            v.clear();
        this.v = new WeakReference<>(view);
        deattachV = false;
    }

    public void dettachView() {
        deattachV = true;
        EventBus.getDefault().unregister(this);
        if (null != mModle)
            mModle.deattach();
        mModle = null;
        if (null != v && null != v.get())
            v.clear();
        v = null;
    }

    @Subscribe
    public void sub(String str) {
    }

    // 抛到ui执行
    public void runOnUI(final Runnable runnable) {
        try {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    e.onNext("");
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            runnable.run();
                        }
                    });
//                            
//                            subscribe(
//                            new Observer<String>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//                                }
//
//                                @Override
//                                public void onNext(String o) {
//                                   
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                }
//
//                                @Override
//                                public void onComplete() {
//                                }
//                            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 提交订阅
    public <T> void toSubscribe(Observable<T> observable, Observer<? super T> observer) throws Exception {
        observable.compose(getComposer()).as(bindLifecycle()).subscribe((Observer<? super Object>) observer);
    }

    // 绑定自动解绑生命周期
    public <T> AutoDisposeConverter<T> bindLifecycle() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(v.get(), Lifecycle.Event.ON_DESTROY));
    }

    // 线程调度器
    public <T> ObservableTransformer<T, T> getComposer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
