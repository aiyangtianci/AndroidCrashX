package com.aiyang.crashx;

import android.content.Context;

import com.aiyang.crashx.init.RealCrash;
import com.aiyang.crashx.inter.ICrash;

public final class CrashX{

    private static ICrash rCrash;


    public static void install(Context mContext) {
        //实例一次，安装一次
        if (rCrash==null){
            rCrash = new RealCrash(mContext);
            rCrash.setUncaughtCrash();
        }
    }

    //扩展功能：设置正式环境、debug环境功能提示不同
    public static void setDebug(){

    }

}
