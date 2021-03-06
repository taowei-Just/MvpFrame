package com.tao.mvpbaselibrary.basic.permissions;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.tao.mvpbaselibrary.basic.permissions.interfaces.PerMissionCall;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


public class PerMissionsManager {
    private static PerMissionsManager perMissionsManager;
    public static PerMissionsManager newInstance(){
        if(perMissionsManager==null){
            synchronized (PerMissionsManager.class){
                perMissionsManager=new PerMissionsManager();
            }
        }
        return perMissionsManager;
    }

    /**
     * 获取相应权限
     * @param mActivity
     * @param missionCall  响应回调
     * @param permissions  权限
     */
    @SuppressLint("CheckResult")
    public void getUserPerMissions(Activity mActivity, final PerMissionCall missionCall, final String... permissions){
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if(aBoolean){
                    missionCall.userPerMissionStatus(true);
                }else {
                    missionCall.userPerMissionStatus(false);
                }
            }
        });

    }

}
