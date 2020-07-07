package com.aiyang.crashx;

import android.content.Context;

import com.aiyang.crashx.initx.RealCrash;
import com.aiyang.crashx.inter.ICrash;
import com.aiyang.crashx.util.Common;
import com.aiyang.crashx.util.Errno;

import me.weishu.freereflection.BuildConfig;

/**
 * CrashX is a crash repair library for Android APP.
 */
public final class CrashX{

    private static ICrash rCrash;

    public CrashX() {
    }

    /**
     * Initialize xCrash with default parameters.
     *
     * <p>Note: This is a synchronous operation.
     *
     * @param ctx The context of the application object of the current process.
     * @return Return zero if successful, non-zero otherwise.
     */
    public static int install(Context ctx) {
        return install(ctx, null);

    }

    public static synchronized int install(Context ctx, InitParameters params) {
        if (Common.FIX_OPENED && rCrash!=null){
            return Errno.OK;
        }
        Common.FIX_OPENED = true;


        if (ctx == null) {
            return Errno.CONTEXT_IS_NULL;
        }

        //make sure to get the instance of android.app.Application
        Context appContext = ctx.getApplicationContext();
        if (appContext != null) {
            ctx = appContext;
        }

        //use default parameters
        if (params == null) {
            params = new InitParameters();
        }
        Common.isDeBug = params.isDeBug;
        Common.FIX_MIAN_KEEPLOOP =params.FIX_MIAN_HHREAD;
        Common.FIX_MIAN_HOOKH =params.FIX_MIAN_HOOKH;
        Common.VIEW_TOUCH_RUNTIOME =params.VIEW_TOUCH_RUNTIOME;
        Common.NOT_FOUND_ACTIVITY =params.NOT_FOUND_ACTIVITY;
        //once instance，once install
        if (rCrash==null){
            rCrash = new RealCrash(ctx);
            rCrash.setUncaughtCrash();
        }

        return Errno.OK;
    }

    public static class InitParameters {
        /*是否开启了测试模式*/
          boolean isDeBug = BuildConfig.DEBUG;

        /*是否开启了拦截主线程，keeploop*/
          boolean FIX_MIAN_HHREAD = true;

        /*是否开启了Activity生命周期方法hook，让异常页面关闭*/
          boolean FIX_MIAN_HOOKH = true;

        /*是否开启了拦截绘制异常，让loop持续*/
          boolean VIEW_TOUCH_RUNTIOME = true;

        /*是否开启了未注册Activity异常处理*/
         boolean NOT_FOUND_ACTIVITY= true;
        /**
         * 可以跳过清单验证Activity注册
         */
        public InitParameters setNotFoundActivity(boolean isTrue) {
            this.NOT_FOUND_ACTIVITY = isTrue;
            return this;
        }

        /**
         * 默认是debug模式，即true开启
         * @param isTrue
         * @return
         */
        public InitParameters setDebug(boolean isTrue) {
            this.isDeBug = isTrue;
            return this;
        }

        /**
         * 唤醒主线程，默认是true开启
         * @param isTrue
         * @return
         */
        public InitParameters setFixUIThread(boolean isTrue) {
            this.FIX_MIAN_HHREAD = isTrue;
            return this;
        }

        /**
         * 关掉异常页面，默认true 开启
         * @param isTrue
         * @return
         */
        public InitParameters setFixActivity(boolean isTrue) {
            this.FIX_MIAN_HOOKH = isTrue;
            return this;
        }

        /**
         * onDraw出错，重启应用。默认true 开启
         * @param isTrue
         * @return
         */
        public InitParameters setFixView(boolean isTrue){
            this.VIEW_TOUCH_RUNTIOME =  isTrue;
            return this;
        }
    }
}
