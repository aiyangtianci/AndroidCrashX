package com.aiyang.android_crashx.utils;

import com.aiyang.android_crashx.utils.SpUtils;

public class Config {
    /**
     * SP存储切换环境
     */
    public static void setSPCrashxOnoff(boolean onoff) {
        SpUtils.putBoolean("switch", onoff);
    }

    public static boolean getSPCrashxOnoff() {
        return SpUtils.getBoolean("switch",true);
    }

}
