package com.aiyang.crashx.initx;

public class Common {
    /*是否开启了拦截，避免重新*/
    public static volatile boolean FIX_OPENED = false;

    /*是否开启了循环，让loop持续*/
    public static boolean FIX_WHILE_OPEN = true;

    /*是否开启了拦截主线程，让loop持续*/
    public static boolean FIX_MIAN_HHREAD = true;



}
