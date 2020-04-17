package com.aiyang.crashx.init;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.aiyang.crashx.inter.ICrash;

public class RealCrash implements Thread.UncaughtExceptionHandler, ICrash {


    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultCaughtExceptionHandler;


    public void setUncaughtCrash(Context mContext) {
        this.mContext = mContext;

        //获取默认的系统异常捕获器
        if (mDefaultCaughtExceptionHandler == null){
            mDefaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        }

        //把当前的crash捕获器设置成默认的crash捕获器
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d("aaa",thread.getName()+"线程");
        if (!handleException(throwable) && mDefaultCaughtExceptionHandler != null) {
            //如果无法拦截处理，则让系统默认的异常处理器来处理
            mDefaultCaughtExceptionHandler.uncaughtException(thread, throwable);

            //重启应用
            Log.d("aaa","handleException: 异常未拦截,待重启");
        }

    }

    /**
     * 自定义错误处理
     * @return true:处理了该异常; 否则返回false
     */
    @Override
    public boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        final String msg = ex.getLocalizedMessage();
        if (msg == null) {
            return false;
        }
        Log.d("aaa","handleException: 异常被拦截,待处理");
//        //使用Toast来显示异常信息
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(mContext, "异常被拦截,待处理", Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        }.start();
        return true;
    }


}
