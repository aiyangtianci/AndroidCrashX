package com.aiyang.android_crashx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.aiyang.android_crashx.mApplication;


/**
 * @author aiyang
 * sharePreference_tool
 */
public final class SpUtils {

    private static SharedPreferences config = mApplication.application.getSharedPreferences("CrashX", Context.MODE_PRIVATE);

    /**
     * 读取字符串
     *
     * @param key 键
     * @return String类型的值
     */
    public static String getString(String key) {
        return config.getString(key, "");
    }

    /**
     * 保持字符串
     *
     * @param key   键
     * @param value 值
     */
    public static synchronized void putString(String key, String value) {
        Editor edit = config.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 读取布尔类型数据
     *
     * @param key     键
     * @param isDebug
     * @return Boolean类型的值
     */
    public static Boolean getBoolean(String key, boolean isDebug) {
        return config.getBoolean(key, isDebug);
    }

    /**
     * 保存布尔类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public synchronized static void putBoolean(String key, Boolean value) {
        Editor edit = config.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * 读取整型类型数据
     *
     * @param key 键
     * @return Int类型的值
     */
    public static Integer getInt(String key) {
        return config.getInt(key, 0);
    }

    /**
     * 保存整型类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public static synchronized void putInt(String key, int value) {
        Editor edit = config.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * 读取浮点型类型数据
     *
     * @param key 键
     * @return Float类型的值
     */
    public static float getFloat(String key) {
        return config.getFloat(key, 0);
    }

    /**
     * 保存浮点型类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public static synchronized void putFloat(String key, float value) {
        Editor edit = config.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    /**
     * 读取长整型类型数据
     *
     * @param key 键
     * @return Long类型的值
     */
    public static long getLong(String key) {
        return config.getLong(key, 0);
    }

    /**
     * 保存长整型类型的数据
     *
     * @param key   键
     * @param value Long值
     */
    public static synchronized void putLong(String key, long value) {
        Editor edit = config.edit();
        edit.putLong(key, value);
        edit.commit();
    }
}