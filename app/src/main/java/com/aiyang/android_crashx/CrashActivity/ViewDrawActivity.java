package com.aiyang.android_crashx.CrashActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aiyang.android_crashx.CrashLog.CrashLogActivity;
import com.aiyang.android_crashx.CrashLog.Log;
import com.aiyang.android_crashx.R;
import com.aiyang.crashx.util.Common;
import com.aiyang.crashx.util.LogFile;
import com.aiyang.crashx.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.aiyang.crashx.util.LogFile.getModifiedTime;

public class ViewDrawActivity extends AppCompatActivity {
    private MyView crashViewone;
    private TextView life_show;
    private ConstraintLayout mLl_parent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        setTitle("View绘制异常处理");
        mLl_parent = findViewById(R.id.mLl_parent);
        crashViewone = findViewById(R.id.crashviewone);
        life_show = findViewById(R.id.life_show);
        readLogFile(life_show);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button on_off = findViewById(R.id.on_off);
        if (Common.VIEW_TOUCH_RUNTIOME){
            on_off.setText("已开启-On");
            on_off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else{
            on_off.setText("关闭-OFF");
            on_off.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    /**
     * 在自身界面，出现绘制异常.
     * 补充:这种情况，是view不可见，再次可见绘制时出现异常.
     */
    public void clickSelfException(View view) {
        crashViewone.isCrash = true;
        crashViewone.setVisibility(View.VISIBLE);
    }

    /**
     * 在自身界面，出现绘制异常.
     * 补充:这种情况，是view已正常显示，再次绘制时出现异常.
     */
    public void clickOtherException(View view) {
        MyView myView = new MyView(this);
        mLl_parent.addView(myView);

        myView.isCrash = true;
        myView.invalidate();
    }

    private void readLogFile(TextView showLog) {
        List<File> fs = LogFile.getCrashFiles(getBaseContext());
        Collections.sort(fs, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return (int) (o2.lastModified() - o1.lastModified());
            }
        });

        final List<Log> logs = new ArrayList<>();
        for (File f : fs) {
            logs.add(new Log(f, f.getName(), null, getModifiedTime(f)));
        }
        if (logs.size() > 0) {
            Log log = logs.get(0);
            showLog.setText("记录📝：\n" + log.title + "\n\t\tLatest：" + log.time);
            showLog.setVisibility(View.VISIBLE);
            showLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //记录查看
                    startActivity(new Intent(ViewDrawActivity.this, CrashLogActivity.class));
                }
            });
        }
    }

    /**
     * View绘制异常处理——开关
     * @param view
     */
    public void click_on_off(final View view) {
        String tilestr ="";
        if (Common.VIEW_TOUCH_RUNTIOME){
            tilestr = "点击确认后，UI绘制出现异常时Crash，将无法运行";
        }else {
            tilestr = "点击确认后，UI绘制出现异常时Crash，将跳回首页";
        }
        Utils.showSimpleDialog(this, "提示", tilestr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Button on_off = (Button)view;
                if (Common.VIEW_TOUCH_RUNTIOME){
                    Common.VIEW_TOUCH_RUNTIOME = false;
                    on_off.setText("关闭-OFF");
                    on_off.setBackgroundColor(getResources().getColor(R.color.gray));
                }else{
                    Common.VIEW_TOUCH_RUNTIOME = true;
                    on_off.setText("已开启-On");
                    on_off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

    }
}
