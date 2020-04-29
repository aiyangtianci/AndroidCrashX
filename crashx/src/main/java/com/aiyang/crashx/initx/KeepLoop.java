package com.aiyang.crashx.initx;

import android.content.Context;
import android.os.Looper;

import com.aiyang.crashx.R;
import com.aiyang.crashx.inter.IKeepLoop;
import com.aiyang.crashx.util.LogUtils;
import com.aiyang.crashx.util.ToastUtil;

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
     * @param t
     */
    @Override
    public void keepLoop(final Context mContext,Thread t) {

            if (Common.FIX_WHILE_OPEN){
                Common.FIX_WHILE_OPEN = false;
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if(isChoreographerException(e)){
                            ToastUtil.show(mContext,mContext.getString(R.string.carsh_canvers));
                        }else{
                            ToastUtil.show(mContext,mContext.getString(R.string.crash_over));
                        }
                        e.printStackTrace();
                    }
                }
            }
    }

    /**
     * 该异常是来自View绘制
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
                //handle e
                return true;
            }
        }
        return false;
    }
}
