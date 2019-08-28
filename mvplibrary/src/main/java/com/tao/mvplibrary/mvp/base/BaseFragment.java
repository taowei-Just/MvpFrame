package com.tao.mvplibrary.mvp.base;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.tao.mvplibrary.mvp.IView;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2019-8-7.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {
    P mPresenter;
    public View mView;
    public Context mcContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getP().attachView(getAttachView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mView = view;
        mcContext = view.getContext();
        initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public IView getAttachView() {
        return this;
    }

    @Override
    public P getP(IView v) {
        if (v == null)
            return getP();

        P presenter = getP();
        if (presenter != null && !presenter.isAttachedV())
            presenter.attachView(v);
        return presenter;
    }


    @Override
    public P getP() {
        if (mPresenter == null) {
            //实例化P层，类似于p = new P();
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<P> clazz = (Class<P>) parameterizedType.getActualTypeArguments()[0];
            try {
                mPresenter = clazz.newInstance();
            } catch (Exception e) {
//                StaticUtils.loge(e.getMessage());

                e.printStackTrace();
            }
        }
        if (mPresenter != null) {
            if (!mPresenter.isAttachedV() && !mPresenter.isDeattachV()) {
                mPresenter.attachView(this);
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
}
