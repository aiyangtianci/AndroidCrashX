package com.aiyang.android_crashx;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.aiyang.android_crashx.CrashActivity.ViewDrawActivity;
import com.aiyang.android_crashx.CrashActivity.NotFoundActivity;
import com.aiyang.android_crashx.CrashActivity.StartLifeActivity;
import com.aiyang.android_crashx.CrashLog.CrashLogActivity;
import com.aiyang.crashx.util.Common;
import com.aiyang.crashx.util.Utils;

import static com.aiyang.android_crashx.R.color.gray;

/**
 * @author aiyang
 * Blog：https://blog.csdn.net/csdn_aiyang
 * Git ：https://github.com/aiyangtianci
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //CrashX:on-off
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void switchCrashX(View view) {
        Button on_off = (Button)view;
        if (Common.FIX_MIAN_KEEPLOOP){
            Common.FIX_MIAN_KEEPLOOP = false;
            on_off.setText("关闭-OFF");
            on_off.setBackgroundColor(getResources().getColor(gray));
        }else{
            Common.FIX_MIAN_KEEPLOOP = true;
            on_off.setText("已开启-On");
            on_off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

   // =========================== JAVA  ===========================

   //（主线程）空指针异常
    public void clickNullPointerException(View view) {
       throw new NullPointerException();
    }

    //（子线程) 数组越界异常
    public void clickIndexOutOfBoundsException(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new IndexOutOfBoundsException();
            }
        }).start();
    }

   // =========================== Activity  ===========================

    //Activity未注册
    public void clickStartActivity(View view) {
        startActivity(new Intent(MainActivity.this, NotFoundActivity.class));
    }

    //自定义View绘制
    public void clickRuntiomeException(View view) {
        startActivity(new Intent(MainActivity.this, ViewDrawActivity.class));
    }

    //Activity生命周期方法异常
    public void clickStartActivity2(View view) {
        startActivity(new Intent(MainActivity.this, StartLifeActivity.class).putExtra("METHOD","onStart"));
    }

    // =========================== Error  ===========================

    //ANR
    public void clickANR(View view) {
//        while (true) {
//            try {
//                Thread.sleep(1000);
//            } catch (Exception ignored) {
//            }
//        }
        Utils.show(this,"敬请期待");
    }

    //OOM
    public void clickOOM(View view) {
        Utils.show(this,"敬请期待");
    }

    // =========================== 错误日志  ===========================

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_log:
                //记录查看
                startActivity(new Intent(MainActivity.this, CrashLogActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

}
