package com.aiyang.crashx.initx;

import android.content.Context;
import android.os.Looper;

import com.aiyang.crashx.R;
import com.aiyang.crashx.inter.IKeepLoop;
import com.aiyang.crashx.util.Common;
import com.aiyang.crashx.util.LogFile;
import com.aiyang.crashx.util.LogUtils;
import com.aiyang.crashx.util.Utils;

/**
 * 迫使Loop持续循环
 * @author aiyang
 */
public final class KeepLoop implements IKeepLoop {

    private static KeepLoop mInstance;

    public static synchronized KeepLoop getInstance(){
        if (mInstance ==null){
            mInstance =  new KeepLoop();
        }
        return  mInstance;
    }

    /**
     * 主线程或子线程抛出异常后，迫使主线程Looper持续loop()
     * @param mContext
     */
    @Override
    public void keepLoop(Thread thread,final Context mContext) {
            if (Common.FIX_WHILE_OPEN){
                Common.FIX_WHILE_OPEN = false;
                while (Common.FIX_MIAN_HHREAD) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        //日志文件
                        LogFile.saveCrashLog(mContext,e);
                        //提示打印
                        if(isChoreographerException(e)){
                            LogUtils.d(mContext.getString(R.string.carsh_canvers));
                            Utils.show(mContext,mContext.getString(R.string.carsh_canvers));
                            canNotCatchCrash(mContext,thread,e);
                        }else{
                            LogUtils.d(mContext.getString(R.string.crash_over));
                            Utils.show(mContext,mContext.getString(R.string.crash_over));
                        }
//                        e.printStackTrace();
                    }
                }
            }
    }

    /**
     * this throwable is from choreographer
     * @param e
     * @return
     */
    @Override
    public boolean isChoreographerException(Throwable e) {
        if (e == null ) {
            return false;
        }
        StackTraceElement[] elements = e.getStackTrace();
        if (elements == null) {
            return false;
        }

        for (int i = elements.length - 1; i > -1; i--) {
            if (elements.length - i > 20) {
                return false;
            }
            StackTraceElement element = elements[i];
            if ("android.view.Choreographer".equals(element.getClassName())
                    && "Choreographer.java".equals(element.getFileName())
                    && "doFrame".equals(element.getMethodName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Uncaught Exception：restartApp  and  To the system default process
     * @param thread
     * @param throwable
     */
    private void canNotCatchCrash(Context mContext,Thread thread, Throwable throwable){
        Utils.restarteApp(mContext);
        Thread.UncaughtExceptionHandler  mDefaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        mDefaultCaughtExceptionHandler.uncaughtException(thread, throwable);
    }


}
