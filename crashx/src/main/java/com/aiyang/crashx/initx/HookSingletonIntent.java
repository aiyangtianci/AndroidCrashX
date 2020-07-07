package com.aiyang.crashx.initx;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.aiyang.crashx.util.Common;
import com.aiyang.crashx.util.LogUtils;
import com.aiyang.crashx.util.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * hook AMS 的 IActivityMannager.aidl接口的Proxy进行替换intent
 * .startActivity(whoThread, who.getBasePackageName(), intent,
 *                         intent.resolveTypeIfNeeded(who.getContentResolver()),
 *                         token, target != null ? target.mEmbeddedID : null,
 *                         requestCode, 0, null, options);
 * 第一个参数是主线程的classloader，
 * 第二个参数是包名(android.app.IActivityManager)，
 * 第三个参数是Intent
 *
 */
public class HookSingletonIntent {
    public static String EXTRA_TARGET_INTENT = "STUB_ACTIVITY";
    /**
     *
     * @param StubActivityName  已注册的占坑Activity类名
     * @param StubActivityPackage 占坑的包名
     */


    public static void Init(final String StubActivityPackage,final String StubActivityName){
        try {
            Field enterField = null;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                Class activityManagerClass = Class.forName("android.app.ActivityManager");
                //Field IActivityManagerSingleton
                enterField = activityManagerClass.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class activityManagerNative = Class.forName("android.app.ActivityManagerNative");
                enterField = activityManagerNative.getDeclaredField("gDefault");
            }
            enterField.setAccessible(true);
            //Singleton<IActivityManager>的实例，因为IActivityManagerSingleton是静态的
            Object singletonObject = enterField.get(null);

            Class singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            // 获取singletonObject中变量mInstance的值即IActivityManager类型的实例
            final Object mIActivityManagerObject = mInstanceField.get(singletonObject);

            //IActivityManager 是AIDL接口，通过动态代理来处理
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class iActivityManagerInterface = Class.forName("android.app.IActivityManager");

            //生产IActivityManager的代理对象
            Object mIActivityManagerProxy = Proxy.newProxyInstance(
                    classLoader, new Class[]{iActivityManagerInterface}, new InvocationHandler() {

                        @Override public Object invoke(Object proxy, Method method, Object[] args)
                                throws Throwable {
                            LogUtils.d( "准备启动activity:"+method.getName());
                            if ("startActivity".equals(method.getName())) {
                                Intent rawIntent = null;
                                int index = 0;
                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        rawIntent = (Intent) args[i];
                                        index = i;
                                        break;
                                    }
                                }
                                // 将需要被启动的Activity替换成StubActivity
                                if (Common.NOT_FOUND_ACTIVITY){
                                    Intent newIntent = new Intent();
                                    newIntent.setComponent(new ComponentName(StubActivityPackage, StubActivityName));
//                              newIntent.setClassName(rawIntent.getComponent().getPackageName(), StubActivity.class.getName());
                                    //把这个newIntent放回到args,达到了一个欺骗AMS的目的
                                    newIntent.putExtra(EXTRA_TARGET_INTENT, rawIntent);
                                    args[index] = newIntent;
                                }
                            }
                            return method.invoke(mIActivityManagerObject, args);
                        }
                    });
            //把我们的代理对象融入到framework
            mInstanceField.set(singletonObject, mIActivityManagerProxy);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mHookHandle();
    }

    private static void mHookHandle(){
        try {
            Class launchActivityClass = Class.forName("android.app.ActivityThread");
            Field mCurrentActivityThreadFiled = launchActivityClass.getDeclaredField("sCurrentActivityThread");
            // 获取ActivityThread的实例
            mCurrentActivityThreadFiled.setAccessible(true);
            Object currentActivityThreadObject = mCurrentActivityThreadFiled.get(null);

            //获取H
            Field mHFiled = launchActivityClass.getDeclaredField("mH");
            //H的实例
            mHFiled.setAccessible(true);
            Handler hObject = (Handler) mHFiled.get(currentActivityThreadObject);

            //获取callback
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            mCallbackField.set(hObject, new Handler.Callback() {
                @Override public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 100:
                            // TODO 需要处理
                            break;
                        case 159:
                            //恢复真身
                            if (Common.NOT_FOUND_ACTIVITY){
                                mHookMsg(msg);
                            }
                            break;
                    }
                    return false;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void mHookMsg(Message msg) {
        Object obj = msg.obj;
        try {
            Field mActivityCallbacksField = obj.getClass().getDeclaredField("mActivityCallbacks");
            mActivityCallbacksField.setAccessible(true);
            List mActivityCallbacks = (List) mActivityCallbacksField.get(obj);
            if (mActivityCallbacks.size() > 0) {
                String className = "android.app.servertransaction.LaunchActivityItem";
                if (mActivityCallbacks.get(0).getClass().getCanonicalName().equals(className)) {
                    Object object = mActivityCallbacks.get(0);
                    Field intentField = object.getClass().getDeclaredField("mIntent");
                    intentField.setAccessible(true);
                    Intent intent = (Intent) intentField.get(object);
                    Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
                    intent.setComponent(targetIntent.getComponent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
