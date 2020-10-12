package com.tao.mvpframe.app;

import android.app.Application;

import com.tao.mvplibrary.app.BaseApplication;


public class MyApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)){return;}
//        LeakCanary.install(this);
    }
}
