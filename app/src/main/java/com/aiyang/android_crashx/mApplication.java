package com.aiyang.android_crashx;

import android.app.Application;

import com.aiyang.crashx.CrashX;

public class mApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CrashX.install(this);
    }
}
