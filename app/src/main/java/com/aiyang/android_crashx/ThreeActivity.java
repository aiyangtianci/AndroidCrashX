package com.aiyang.android_crashx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ThreeActivity extends AppCompatActivity {
    private String LIFE_METHOD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        LIFE_METHOD = getIntent().getStringExtra("METHOD");
        if ("onCreate".equals(LIFE_METHOD)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if ("onStart".equals(LIFE_METHOD)) {
            throw new RuntimeException();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if ("onRestart".equals(LIFE_METHOD)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ("onResume".equals(LIFE_METHOD)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ("onPause".equals(LIFE_METHOD)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ("onStop".equals(LIFE_METHOD)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ("onDestroy".equals(LIFE_METHOD)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        throw  new RuntimeException();
    }

    public void clickRuntiomeException(View view) {
        throw new RuntimeException();
    }
}
