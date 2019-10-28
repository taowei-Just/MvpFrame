package com.tao.mvpframe.ui;

import android.Manifest;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.tao.mvpframe.R;
import com.tao.mvpframe.contract.MainActivtyContract;
import com.tao.mvpframe.presenter.MainActivityPresent;
import com.tao.mvplibrary.mvp.base.BaseActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity<MainActivityPresent> implements MainActivtyContract.IMainActivtyView<MainActivityPresent> {
    @Override
    public void beforeSetContentView() {
        noTitel();
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


}
