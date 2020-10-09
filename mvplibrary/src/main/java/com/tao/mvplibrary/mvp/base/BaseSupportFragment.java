package com.tao.mvplibrary.mvp.base;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tao.mvplibrary.mvp.IView;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019-8-7.
 */

public abstract class BaseSupportFragment<P extends BasePresenter> extends Fragment implements IBaseView<P> {
    P mPresenter;
    public View mView;
    public Context mcContext;
    private Unbinder bind;
    private IView attachView;

    public  static  <T extends BaseSupportFragment> T getInstance(Class<T> tClass) throws Exception {
        return tClass.newInstance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            attachView = getAttachView();
            getP( attachView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        mView = view;
        mcContext = view.getContext();
        initView();
    }

    @Override
    public void initView() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void initData() {

    }

    public IView getAttachView() {
        return this;
    }

    @Override
    public P getP(IView v)  throws Exception{
        if (v == null)
            return getP();

        P presenter = getP();
        if (presenter != null && !presenter.isAttachedV())
            presenter.attachView(v);
        return presenter;
    }


    @Override
    public P getP() throws Exception{
        if (mPresenter == null) {
            //实例化P层，类似于p = new P();
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<P> clazz = (Class<P>) parameterizedType.getActualTypeArguments()[0];
                mPresenter = clazz.newInstance();
             
        }
        if (mPresenter != null&& attachView ==null) {
            try {
                if (!mPresenter.isAttachedV() && !mPresenter.isDeattachV()) {
                    mPresenter.attachView(this);
                }
            } catch (Exception e) {
            }
        }
        return mPresenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.dettachView();
    }

    @Override
    public Lifecycle getLifecycle() {
        return new LifecycleRegistry(this);
    }
}
