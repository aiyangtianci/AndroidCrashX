package com.aiyang.crashx.initx;

import android.content.Context;
import android.os.Looper;

import com.aiyang.crashx.R;
import com.aiyang.crashx.inter.ICrash;
import com.aiyang.crashx.inter.IKeepLoop;
import com.aiyang.crashx.util.Common;
import com.aiyang.crashx.util.LogFile;
import com.aiyang.crashx.util.LogUtils;
import com.aiyang.crashx.util.Utils;

/**
 * @author aiyang
 */
public final class RealCrash implements Thread.UncaughtExceptionHandler, ICrash {

    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultCaughtExceptionHandler;

    private IKeepLoop keepLoop;

    public RealCrash(Context mContext) {
        this.mContext = mContext;
        keepLoop = KeepLoop.getInstance();
        if (Common.FIX_MIAN_HOOKH){
            ActivityKiller.Init(mContext);
        }
    }

    /**
     * Gets the default system exception trap and sets the current crash trap to the default crash trap
     */
    @Override
    public void setUncaughtCrash() {
        if (mDefaultCaughtExceptionHandler == null){
            mDefaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * handle uncaughtException method
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        LogUtils.d("UnCaughtException："+thread.toString());
        if (!handleException(thread,throwable)) {
            mDefaultCaughtExceptionHandler.uncaughtException(thread, throwable);
        }
    }

    /**
     * handle Thread Exception
     * {
     * 1: The default intercepts child threads;
     * 2: You can set whether to intercept the main thread exception and keep the application running;
     * 3: Drawing an exception suggests closing the page
     * }
     * @return true;false
     */
    private boolean handleException(Thread t,Throwable tw) {
        if (tw ==null) {
            return  false;
        }
        //Log记录
        LogFile.saveCrashLog(mContext, tw);
        if (t == Looper.getMainLooper().getThread()){
            if (Common.FIX_MIAN_KEEPLOOP){
                //提示
                if (keepLoop.isChoreographerException(tw)){
                    LogUtils.d(mContext.getString(R.string.carsh_canvers));
                    Utils.show(mContext,mContext.getString(R.string.carsh_canvers));
                    if (Common.VIEW_TOUCH_RUNTIOME){
                        keepLoop.onDrawCrashKeepRun(mContext,t, tw);
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    LogUtils.d(mContext.getString(R.string.crash_tip2));
                    Utils.show(mContext,mContext.getString(R.string.crash_tip2));
                    keepLoop.keepLoop(t,mContext);
                    return true;
                }
            }
        }else{
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    //提示
                    LogUtils.d(mContext.getString(R.string.crash_tip1));
                    Utils.show(mContext,mContext.getString(R.string.crash_tip1));
                    Looper.loop();
                }
            }.start();
            return true;
        }
        return false;
    }
}
