package com.tao.xmvplibrary.mvp.base;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.tao.xmvplibrary.mvp.IView;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019-8-7.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView<P> {

    private P mPresenter;
    private Unbinder bind;
    private IView attachView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            attachView = getAttachView();
            getP().attachView(attachView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        beforeCreate();
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initView();
        initData();
    }

    public void beforeCreate() {
        
    }

    /**
     * 在用于设的contentview之前
     * 多用于窗口的设置
     */
    public void beforeSetContentView() {
    }

    @Override
    public void initView() {

    }

    /**
     * 初始化数据
     */
    public void initData() {
    }

    @Override
    protected void onDestroy() {
        if (null != mPresenter)
            mPresenter.dettachView();
        super.onDestroy();
    }

    @Override
    public P getP(IView v) throws Exception {
        if (v == null)
            return getP();
        P presenter = getP();
        if (presenter != null && !presenter.isAttachedV())
            presenter.attachView(v);
        return presenter;
    }


    @Override
    public P getP() throws Exception {
        if (mPresenter == null) {
            //实例化P层，类似于p = new P();
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<P> clazz = (Class<P>) parameterizedType.getActualTypeArguments()[0];
            mPresenter = clazz.newInstance();
        }
        if (mPresenter != null) {
            if (!mPresenter.isAttachedV() && !mPresenter.isDeattachV()) {
                mPresenter.attachView(this);
            }
        }
        return mPresenter;
    }

    protected IView getAttachView() {
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View ve = getCurrentFocus();
            if (isShouldHideKeyboard(ve, ev)) {
                boolean res = hideKeyboard(this, ve.getWindowToken());
            }
        }

        return super.dispatchTouchEvent(ev);
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
     * 最好使用post隐藏
     *
     * @param token 焦点view的token
     */
    private static boolean hideKeyboard(Context context, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            return im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    public void noTitle(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
    // 去掉默认的actionbar
    public void noActionBar() {

        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.hide();
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

    // 全屏
    public void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
 

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setSystemUiVisibility();
    }

    /**
     * 
     * 
     * 操作系统状态栏
     */
    
    public void setSystemUiVisibility() {
      
       
    }
 
}
