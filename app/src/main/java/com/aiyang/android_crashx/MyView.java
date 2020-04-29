package com.aiyang.android_crashx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.aiyang.crashx.util.LogUtils;

@SuppressLint("AppCompatCustomView")
public class MyView extends Button {
    public boolean isTouch = false;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //抛出异常
        if (isTouch){
            throw new RuntimeException();
        }
    }
}
