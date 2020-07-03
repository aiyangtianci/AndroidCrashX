package com.aiyang.android_crashx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.aiyang.android_crashx.CrashActivity.FourActivity;
import com.aiyang.android_crashx.CrashActivity.SecondActivity;
import com.aiyang.android_crashx.CrashActivity.ThreeActivity;
import com.aiyang.android_crashx.CrashLog.CrashLogActivity;
import com.aiyang.crashx.CrashX;
import com.aiyang.crashx.util.Common;
import com.aiyang.crashx.util.Utils;

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
    public void switchCrashX(View view) {
        if (Common.FIX_MIAN_HHREAD){
            Common.FIX_MIAN_HHREAD = false;
        }else{
            Common.FIX_MIAN_HHREAD = true;
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
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }

    //自定义View绘制
    public void clickRuntiomeException(View view) {
        startActivity(new Intent(MainActivity.this, FourActivity.class));
    }

    //Activity生命周期方法异常
    public void clickStartActivity2(View view) {
        startActivity(new Intent(MainActivity.this, ThreeActivity.class).putExtra("METHOD","onStart"));
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
