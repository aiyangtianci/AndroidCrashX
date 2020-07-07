package com.aiyang.android_crashx;

import android.app.Application;

import com.aiyang.crashx.CrashX;

public class mApplication extends Application {

    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

//        CrashX.install(this);
//===================TEST DEMO=======================
        // 打包上线设为flase，不进行相关log、toast提示
        CrashX.install(this, new CrashX.InitParameters()
                .setDebug(true)
                .setFixUIThread(true)
                .setFixActivity(true)
                .setNotFoundActivity(true)
                .setFixView(true));
    }
}
