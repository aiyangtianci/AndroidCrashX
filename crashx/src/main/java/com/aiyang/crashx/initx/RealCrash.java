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
    }

    @Override
    public void setUncaughtCrash() {
        if (!Common.FIX_OPENED){
            Common.FIX_OPENED = true;
        }else{
            return;
        }

        //获取默认的系统异常捕获器
        if (mDefaultCaughtExceptionHandler == null){
            mDefaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        }

        //把当前的crash捕获器设置成默认的crash捕获器
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    /**
     * handle uncaughtException method
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        LogUtils.d("出现异常："+thread.toString());
        if (!handleException(thread,throwable)) {
            canNotCatchCrash(thread, throwable);
        }
    }


    /**
     * handle Thread Exception
     * @return true:拦截了该异常; 否则返回false
     */
    private boolean handleException(Thread t,Throwable tw) {
        if (tw ==null) {
            return  false;
        }
        if (t == Looper.getMainLooper().getThread()){
            if (Common.FIX_MIAN_HHREAD){
                if (keepLoop.isChoreographerException(tw)){
                    ToastUtil.show(mContext,mContext.getString(R.string.carsh_canvers));
                }
                keepLoop.keepLoop(mContext,t);
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
     * Uncaught Exception
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
