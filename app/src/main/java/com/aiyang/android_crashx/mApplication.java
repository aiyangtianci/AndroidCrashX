package com.aiyang.android_crashx;

import android.app.Application;

import com.aiyang.crashx.CrashX;

public class mApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CrashX.install(this);

       // 打包上线设为flase，不进行相关log、toast提示
        // CrashX.install(this,new CrashX.InitParameters().setDebug(false));
    }
}
