package com.tao.mvpframe.app;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)){return;}
//        LeakCanary.install(this);
    }
}
