package com.aiyang.crashx.util;

public class Common {
    /*是否测试模式*/
    public static boolean isDeBug = true;

    /*是否开启了拦截，避免重新*/
    public static volatile boolean FIX_OPENED = false;

    /*是否开启了循环，让loop持续*/
    public static boolean FIX_WHILE_OPEN = true;

    /*是否开启了拦截主线程，让loop持续*/
    public static boolean FIX_MIAN_HHREAD = true;

    /*是否开启了Activity生命周期方法hook，让异常页面关闭*/
    public static boolean FIX_MIAN_HOOKH = true;

    /*是否开启了拦截绘制异常，让loop持续*/
    public static boolean VIEW_TOUCH_RUNTIOME = true;
}
