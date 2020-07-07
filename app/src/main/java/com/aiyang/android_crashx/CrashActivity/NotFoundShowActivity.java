package com.aiyang.android_crashx.CrashActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aiyang.android_crashx.CrashLog.CrashLogActivity;
import com.aiyang.android_crashx.R;
import com.aiyang.crashx.initx.HookSingletonIntent;
import com.aiyang.crashx.inter.ICrashLogFile;
import com.aiyang.crashx.util.Common;
import com.aiyang.crashx.util.LogFile;
import com.aiyang.crashx.util.Utils;

public class NotFoundShowActivity extends AppCompatActivity implements ICrashLogFile {
    private TextView life_show;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notfound);
        setTitle("Activity未注册处理");
        life_show = findViewById(R.id.life_show);
        LogFile.addCrashLogFileListener(this);

        //单独设置占坑的Activity
        HookSingletonIntent.Init(getPackageName(),StubActivity.class.getName());
    }

    /**
     * 跳转到未在清单注册的Activity
     * @param view
     */
    public void startactivity(View view) {
        startActivity(new Intent(NotFoundShowActivity.this, NotFoundActivity.class));
    }

    /**
     * 是否开启处理ActivityNotFoundException
     * @param view
     */
    public void click_on_off(final View view) {
        String tilestr ="";
        if (Common.NOT_FOUND_ACTIVITY){
            tilestr = "点击确认后，未注册的Activity，将无法打开";
        }else {
            tilestr = "点击确认后，未注册的Activity，将正常打开";
        }
        Utils.showSimpleDialog(this, "提示", tilestr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Button on_off = (Button)view;
                if (Common.NOT_FOUND_ACTIVITY){
                    Common.NOT_FOUND_ACTIVITY = false;
                    on_off.setText("关闭-OFF");
                    on_off.setBackgroundColor(getResources().getColor(R.color.gray));
                }else{
                    Common.NOT_FOUND_ACTIVITY = true;
                    on_off.setText("已开启-On");
                    on_off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button on_off = findViewById(R.id.on_off);
        if (Common.NOT_FOUND_ACTIVITY){
            on_off.setText("已开启-On");
            on_off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else{
            on_off.setText("关闭-OFF");
            on_off.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void OnNewCrashListener(String name, String time) {
        life_show.setText("记录📝：\n" +name+ "\n\t\tLatest：" + time);
        life_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //记录查看
                startActivity(new Intent(NotFoundShowActivity.this, CrashLogActivity.class));
            }
        });
    }
}
