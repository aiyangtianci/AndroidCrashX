package com.aiyang.crashx.inter;

import android.content.Context;

public interface IKeepLoop {
    /**
     * 主线程或子线程抛出异常后，迫使主线程Looper持续loop()
     */
    void  keepLoop(Context mContext,Thread t);
}
