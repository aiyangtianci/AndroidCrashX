package com.aiyang.android_crashx;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aiyang.crashx.util.Common;

public class FourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
    }

    //自定义View绘制
    public void clickRuntiomeException(View view) {
        MyView myView = (MyView) view;
        Common.VIEW_TOUCH_RUNTIOME = true;
        myView.invalidate();
    }
}
