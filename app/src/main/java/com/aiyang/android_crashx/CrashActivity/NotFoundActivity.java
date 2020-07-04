package com.aiyang.android_crashx.CrashActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity Not Found Exception
 * cause：
 * 1.启动的activity没有在AndroidManifest.xml里声明。
 *
 * 2.在AndroidManifest.xml里声明的位置错误，例如Activity标签放在了Application标签外面。
 *
 * 3.要启动的Activity位于其他包里面，要注意声明的路径写法是否正确。
 *
 * 4.想启动某个系统Activity，但是在某些定制rom上不存在这个系统Activity
 */
public class NotFoundActivity extends AppCompatActivity {
}
