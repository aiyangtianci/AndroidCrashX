package com.aiyang.android_crashx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.aiyang.android_crashx.CrashActivity.NotFoundShowActivity;
import com.aiyang.android_crashx.CrashActivity.StartLifeActivity;
import com.aiyang.android_crashx.CrashActivity.ViewDrawActivity;
import com.aiyang.android_crashx.CrashLog.CrashLogActivity;
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
    public void switchCrashX(final View view) {
        String tilestr ="";
        if (Common.FIX_MIAN_KEEPLOOP){
            tilestr = "点击确认后，系统出现异常时Crash，将无法运行";
        }else {
            tilestr = "点击确认后，系统出现异常时Crash，将继续运行";
        }
        Utils.showSimpleDialog(this, "提示", tilestr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Button on_off = (Button)view;
                if (Common.FIX_MIAN_KEEPLOOP){
                    Common.FIX_MIAN_KEEPLOOP = false;
                    on_off.setText("关闭-OFF");
                    on_off.setBackgroundColor(getResources().getColor(R.color.gray));
                }else{
                    Common.FIX_MIAN_KEEPLOOP = true;
                    on_off.setText("已开启-On");
                    on_off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Button on_off = findViewById(R.id.switchCrashX);
        if (Common.FIX_MIAN_KEEPLOOP){
            on_off.setText("已开启-On");
            on_off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else{
            on_off.setText("关闭-OFF");
            on_off.setBackgroundColor(getResources().getColor(R.color.gray));
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
        startActivity(new Intent(MainActivity.this, NotFoundShowActivity.class));
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
        menu.findItem(R.id.action_log).setTitle("LogCat 记录    ");
        return true;
    }
}
