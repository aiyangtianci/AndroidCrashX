package com.aiyang.crashx.initx;

import android.content.Context;
import android.os.Looper;

import com.aiyang.crashx.R;
import com.aiyang.crashx.inter.ICrash;
import com.aiyang.crashx.util.LogUtils;
import com.aiyang.crashx.util.ToastUtil;

/**
 * @author aiyang
 */
public final class RealCrash implements Thread.UncaughtExceptionHandler, ICrash {

    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultCaughtExceptionHandler;

    private  KeepLoop keepLoop;

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
        if (!Common.FIX_OPENED){
            Common.FIX_OPENED = true;
        }else{
            return;
        }
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
            canNotCatchCrash(thread, throwable);
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
        if (t == Looper.getMainLooper().getThread()){
            if (Common.FIX_MIAN_HHREAD){
                if (keepLoop.isChoreographerException(tw)){
                    ToastUtil.show(mContext,mContext.getString(R.string.carsh_canvers));
                }else{
                    ToastUtil.show(mContext,mContext.getString(R.string.crash_tip2));
                }
                keepLoop.keepLoop(mContext);
            }else{
                canNotCatchCrash(t,tw);
            }
        }else{
            LogUtils.d(mContext.getString(R.string.crash_tip1));
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    ToastUtil.show(mContext,mContext.getString(R.string.crash_tip1));
                    Looper.loop();
                }
            }.start();
        }
        return true;
    }

    /**
     * Uncaught Exception：To the system default process
     * @param thread
     * @param throwable
     */
    private void canNotCatchCrash(Thread thread, Throwable throwable){
        if (mDefaultCaughtExceptionHandler != null){
            mDefaultCaughtExceptionHandler.uncaughtException(thread, throwable);
            ToastUtil.show(mContext,mContext.getString(R.string.carsh_noway));
        }
    }

}
