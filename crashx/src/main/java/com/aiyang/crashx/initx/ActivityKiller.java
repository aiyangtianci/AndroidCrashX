package com.aiyang.crashx.initx;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.aiyang.crashx.initx.activitykillercompat.ActivityKillerV15_V20;
import com.aiyang.crashx.initx.activitykillercompat.ActivityKillerV21_V23;
import com.aiyang.crashx.initx.activitykillercompat.ActivityKillerV24_V25;
import com.aiyang.crashx.initx.activitykillercompat.ActivityKillerV26;
import com.aiyang.crashx.initx.activitykillercompat.ActivityKillerV28;
import com.aiyang.crashx.inter.IActivityKiller;

import java.lang.reflect.Field;

/**
 * If ignore the life cycle of abnormal words will directly lead to black screen,
 * Now FinishActivity ends an Activity whose lifecycle throws an exception.
 */
public class ActivityKiller {
    /**
     * Different versions of android get ActivityManager, finishActivity parameter, token(binder object) is different
     */
    public static void Init(Context mContext) {
        IActivityKiller mInstance = null;
        if (Build.VERSION.SDK_INT >= 28) { // android 9.0
            mInstance = new ActivityKillerV28(mContext);
        } else if (Build.VERSION.SDK_INT >= 26) {//android 8.0
            mInstance = new ActivityKillerV26();
        } else if (Build.VERSION.SDK_INT == 25 || Build.VERSION.SDK_INT == 24) { //android 7.0 -7.1.1
            mInstance = new ActivityKillerV24_V25();
        } else if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 23) { //android 5.0  -6.0
            mInstance = new ActivityKillerV21_V23();
        } else if (Build.VERSION.SDK_INT <= 20){//android 4.4
            mInstance = new ActivityKillerV15_V20();
        }
        try {
            mHookmH(mInstance,mContext);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * Replace ActivityThread. MH. MCallback, realizes the intercept the Activity lifecycle
     */
    private static void mHookmH(final IActivityKiller mKill,Context mC) throws Exception{
        if (mKill == null){
           throw new NullPointerException("mKill is null");
        }
        final int LAUNCH_ACTIVITY = 100;
        final int PAUSE_ACTIVITY = 101;
        final int PAUSE_ACTIVITY_FINISHING = 102;
        final int STOP_ACTIVITY_HIDE = 104;
        final int RESUME_ACTIVITY = 107;
        final int DESTROY_ACTIVITY = 109;
        final int NEW_INTENT = 112;
        final int RELAUNCH_ACTIVITY = 126;
        final int EXECUTE_TRANSACTION = 159;
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getDeclaredMethod("currentActivityThread").invoke(null);

        Field mhField = activityThreadClass.getDeclaredField("mH");
        mhField.setAccessible(true);
        final Handler mhHandler = (Handler) mhField.get(activityThread);
        Field callbackField = Handler.class.getDeclaredField("mCallback");
        callbackField.setAccessible(true);
        callbackField.set(mhHandler, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (Build.VERSION.SDK_INT >= 28) {
                    if (msg.what == EXECUTE_TRANSACTION) {
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            mKill.finishLaunchActivity(msg);
                        }
                        return true;
                    }
                    return false;
                }
                switch (msg.what) {
                    case LAUNCH_ACTIVITY:// startActivity--> activity.attach  activity.onCreate
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            mKill.finishLaunchActivity(msg);
                        }
                        return true;
                    case RESUME_ACTIVITY://回到activity onRestart onStart onResume
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            mKill.finishResumeActivity(msg);
                        }
                        return true;
                    case PAUSE_ACTIVITY_FINISHING://按返回键 onPause
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            mKill.finishPauseActivity(msg);
                        }
                        return true;
                    case PAUSE_ACTIVITY://开启新页面时，旧页面执行 activity.onPause
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            mKill.finishPauseActivity(msg);
                        }
                        return true;
                    case STOP_ACTIVITY_HIDE://开启新页面时，旧页面执行 activity.onStop
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            mKill.finishStopActivity(msg);
                        }
                        return true;
                    case DESTROY_ACTIVITY:// 关闭activity onStop  onDestroy
                        try {
                            mhHandler.handleMessage(msg);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        return true;
                }
                return false;
            }
        });
    }
}
