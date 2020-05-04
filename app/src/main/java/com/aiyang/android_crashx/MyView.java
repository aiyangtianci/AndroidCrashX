package com.aiyang.android_crashx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.Nullable;
import com.aiyang.crashx.util.Common;

@SuppressLint("AppCompatCustomView")
public class MyView extends Button {

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
        if (Common.VIEW_TOUCH_RUNTIOME){
            throw new RuntimeException();
        }
    }
}
