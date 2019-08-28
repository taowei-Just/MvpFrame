package com.tao.mvplibrary.mvp.base;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.tao.mvplibrary.mvp.IView;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2019-8-7.
 */

public abstract class BaseDialogFragment<P extends BasePresenter> extends DialogFragment implements IBaseView, View.OnTouchListener {
    P mPresenter;
    public View mView;
    public Context mContext;
    private OnDimssListener onDimssListener;
    private Dialog mDialog;

    public void setOnDimssListener(OnDimssListener onDimssListener) {
        this.onDimssListener = onDimssListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        try {
            hideKeyboard(mContext, mDialog.getCurrentFocus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDismiss(dialog);
        if (null != mPresenter)
            mPresenter.dettachView();
        if (null != onDimssListener)
            onDimssListener.onDismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getP().attachView(getAttachView());

    }

    protected IView getAttachView() {
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
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
        mContext = view.getContext();
//        view.setOnTouchListener(this);
        initView();
        mDialog = getDialog();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getDecorView().setOnTouchListener(this);
        getP();
        initData();
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
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View ve = getDialog().getCurrentFocus();
            if (isShouldHideKeyboard(ve, event)) {
                try {
                    hideKeyboard(mContext, ve);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public interface OnDimssListener {
        void onDismiss();
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param 
     */
    public static void hideKeyboard(Context context, View view) throws Exception{
        if (view != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
             im.hideSoftInputFromWindow(view.getWindowToken(), 0);
//             im.showSoftInput(view,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(Context context, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.showSoftInputFromInputMethod(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}