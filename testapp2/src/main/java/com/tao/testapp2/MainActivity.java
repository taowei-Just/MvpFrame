package com.tao.testapp2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.tao.mvpbaselibrary.basic.utils.ToastUtil;
import com.tao.mvpbaselibrary.mvp.base.BaseObserver;
import com.tao.mvpframe.test.contract.MainActivtyContract;
import com.tao.mvpframe.test.presenter.MainActivityPresent;
import com.tao.mvplibrary.mvp.IView;
import com.tao.mvplibrary.mvp.base.BaseActivity;
import com.tao.mvplibrary.utils.RxUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;


public class MainActivity extends BaseActivity<MainActivityPresent> implements MainActivtyContract.IMainActivtyView<MainActivityPresent> {

    @Override
    public void beforeSetContentView() {
        noActionBar();
        fullScreen();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View mContextView) {
    }

    @SuppressLint("AutoDispose")
    @Override
    public void initData() {
        ToastUtil.init(getApplicationContext());
        RxPermissions rxPermissions = new RxPermissions(this);
        RxUtils.toSubscribe(rxPermissions.requestEach(Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                new BaseObserver<Permission>() {
                    @Override
                    protected void accept(Permission permission) {
                        if (permission.granted) {
                            // 用户允许权限
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户单次拒绝权限                    
                        } else {
                            // 用户拒绝权限并不再询问
                        }
                    }
                }, getLifecycle());

    }

    public void test(View view) {
        try {
            getP().test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void post(View view) {
        try {
            getP().testPost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushFiles(View view) {
        try {
            getP().testPostFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void switchui(View view) {
        try {
            getP().switchUi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test001(View view) {
        try {
            getP().test001();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test002(View view) {
        ToastUtil.shortShow(" jump 2 mainactivity 2");
        startActivity(new Intent(this, MainActivity2.class));

    }

    @Override
    protected IView getAttachView() {
        return new Myview();
    }

    class Myview implements IView<MainActivityPresent> {

        @Override
        public MainActivityPresent getP() throws Exception {
            return MainActivity.this.getP();
        }

        @Override
        public MainActivityPresent getP(IView view) throws Exception {
            return MainActivity.this.getP(view);
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return MainActivity.this.getLifecycle();
        }
    }
}
