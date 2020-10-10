package com.tao.mvpframe.ui;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;


 

import com.tao.mvpframe.R;
import com.tao.mvpframe.test.contract.MainActivtyContract;
import com.tao.mvpframe.test.presenter.MainActivityPresent;
import com.tao.mvplibrary.mvp.IView;
import com.tao.mvplibrary.mvp.base.BaseActivity;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


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
    public void initView() {
    }

    @Override
    public void initData() {

        if (!EasyPermissions.hasPermissions(this, Manifest.permission.INTERNET))
            EasyPermissions.requestPermissions(this, "internet", 1000, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            EasyPermissions.requestPermissions(this, "internet", 1000, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, new EasyPermissions.PermissionCallbacks() {
            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                Log.e("tag", "onRequestPermissionsResult");
            }

            @Override
            public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
                Log.e("tag", "onPermissionsGranted");
            }

            @Override
            public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
                Log.e("tag", "onPermissionsDenied");
            }
        });
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

    @Override
    protected IView getAttachView() {
        return new Myview();
    }

    class  Myview implements IView<MainActivtyContract.IMainActivtyPresenter> {

        @Override
        public MainActivtyContract.IMainActivtyPresenter getP() throws Exception {
            return MainActivity.this.getP();
        }

        @Override
        public MainActivtyContract.IMainActivtyPresenter getP(IView view) throws Exception {
            return MainActivity.this.getP(view);
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return MainActivity.this.getLifecycle();
        }
    }
}
