package com.aiyang.crashx;

import android.content.Context;

import com.aiyang.crashx.init.RealCrash;

public final class CrashX{

    private static  RealCrash rCrash;


    public static void install(Context mContext) {
        if (rCrash==null){
            rCrash = new RealCrash();
        }
        rCrash.setUncaughtCrash(mContext);
    }

    //扩展功能：设置正式环境、debug环境
    public static void setDebug(){}

}
