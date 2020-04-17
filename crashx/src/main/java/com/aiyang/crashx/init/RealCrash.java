package com.aiyang.crashx.init;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.aiyang.crashx.R;
import com.aiyang.crashx.inter.ICrash;
import com.aiyang.crashx.util.LogUtils;
import com.aiyang.crashx.util.ToastUtil;

/**
 * @author aiyang
 */
public class RealCrash implements Thread.UncaughtExceptionHandler, ICrash {

    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultCaughtExceptionHandler;

    private  KeepLoop keepLoop;

    public RealCrash(Context mContext) {
        this.mContext = mContext;
        keepLoop = KeepLoop.getInstance();
    }

    /**
     * 拦截系统异常捕获器
     */
    @Override
    public void setUncaughtCrash() {
        //获取默认的系统异常捕获器
        if (mDefaultCaughtExceptionHandler == null){
            mDefaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        }

        //把当前的crash捕获器设置成默认的crash捕获器
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        LogUtils.d("出现异常："+thread.toString());
        if (!handleException(throwable) && mDefaultCaughtExceptionHandler != null) {
            //如果无法拦截处理，则让系统默认的异常处理器来处理
            mDefaultCaughtExceptionHandler.uncaughtException(thread, throwable);

            //建议重启应用
            ToastUtil.show(mContext,mContext.getString(R.string.carsh_noway));
        }else{
            keepLoop.keepLoop(mContext,thread);
        }
    }

    /**
     * 错误处理检测
     * @return true:可以拦截该异常; 否则返回false
     */
    @Override
    public boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        String msg = ex.toString();

        LogUtils.d("异常允许被拦截："+msg);
        return true;
    }


}
