package com.aiyang.android_crashx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    =========================== JAVA  ===========================
    */
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

    /*
    =========================== Activity  ===========================
     */

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

    /*
    =========================== ANR  ===========================
     */
    //ANR
    public void clickANR(View view) {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }
}
