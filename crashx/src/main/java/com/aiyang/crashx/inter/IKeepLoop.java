package com.aiyang.crashx.inter;

import android.content.Context;

public interface IKeepLoop {
    /**
     * 主线程或子线程抛出异常后，迫使主线程Looper持续loop()
     */
    void  keepLoop(Thread thread,Context mContext);

    /**
     * 绘制时抛出异常
     * @param e
     */
    boolean isChoreographerException(Throwable e);
}
