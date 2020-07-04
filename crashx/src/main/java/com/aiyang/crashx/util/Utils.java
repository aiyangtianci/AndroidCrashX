package com.aiyang.crashx.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {

    /**
     * 重启app
     * @param context
     */
    public static void restarteApp(Context context) {
        Intent intent = getIntentByPackageName(context, context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     * 根据包名获取意图
     * @param context     上下文
     * @param packageName 包名
     * @return 意图
     */
    private static Intent getIntentByPackageName(Context context, String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }

    private static Toast toast = null;

    /**
     * isDebug :是用来控制，是否打印日志
     */

    public static void show(Context context, String text) {
        if (context == null) {
            return;
        }
        // 创建一个Toast提示消息
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        // 设置Toast提示消息在屏幕上的位置
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        // 显示消息
        if (Common.isDeBug){
            toast.show();
        }
    }



    /**
     *  显示单独按钮的Dialog
     */
    public static void showSimpleDialog(Activity context, String title, String message , DialogInterface.OnClickListener clickListener) {
        AlertDialog alertDialog;
       AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        //监听事件
        if (clickListener != null) {
            builder.setPositiveButton("确认", clickListener);
        } else {
            builder.setNegativeButton("知道了", null);
        }
        builder.setNegativeButton("算了，考虑一下",null);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        if (!((Activity) context).isFinishing()) {
            alertDialog.show();
        }
    }
}
