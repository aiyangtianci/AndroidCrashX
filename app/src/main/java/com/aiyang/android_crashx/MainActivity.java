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

    public void clickNullPointerException(View view) {

       throw new NullPointerException();
    }

    public void clickIndexOutOfBoundsException(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new IndexOutOfBoundsException();
            }
        }).start();
    }

    public void clickANR(View view) {

    }

    public void clickStartActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}
