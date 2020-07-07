package com.aiyang.crashx.util;

/**
 * 记录：不需初始化设置，是用来避免功能重复开启。
 */
public final class Common {

    /*记录安装*/
    public static volatile boolean FIX_OPENED = false;
    /*记录循环*/
    public static boolean FIX_WHILE_OPEN = true;

//=======================================================
    /*是否开启了测试模式*/
    public static boolean isDeBug = true;

    /*是否开启了拦截主线程，keeploop*/
    public static boolean FIX_MIAN_KEEPLOOP = true;


    /*是否开启了Activity生命周期方法hook，让异常页面关闭*/
    public static boolean FIX_MIAN_HOOKH = true;

    /*是否开启了布局xml绘制异常处理*/
    public static boolean VIEW_TOUCH_RUNTIOME = true;

    /*是否开启了未注册Activity异常处理*/
    public static boolean NOT_FOUND_ACTIVITY = true;
}
