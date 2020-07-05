package com.aiyang.android_crashx.CrashActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aiyang.android_crashx.R;

public class NotFoundDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notfound);
        setTitle("Activity未注册处理");

    }

    public void startactivity(View view) {
        startActivity(new Intent(NotFoundDetailActivity.this, NotFoundActivity.class));
    }
}
