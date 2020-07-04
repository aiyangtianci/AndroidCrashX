package com.aiyang.android_crashx.CrashLog;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiyang.android_crashx.CrashLog.Log;
import com.aiyang.android_crashx.CrashLog.LogAdapter;
import com.aiyang.android_crashx.R;
import com.aiyang.crashx.util.LogFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.aiyang.crashx.util.LogFile.getModifiedTime;

/**
 * 错误日志
 * @author aiyang
 */
public class CrashLogActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LogAdapter adapter;
    Handler fileReadHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_log);
        setTitle("Logcat 记录");
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter = new LogAdapter(this,fileReadHandler));

        readFileList();
    }


    private void readFileList() {
        fileReadHandler.post(new Runnable() {
            @Override
            public void run() {
                String dir = LogFile.crashLogDir(getBaseContext());
                if (dir == null) {
                    return;
                }
                File file = new File(dir);
                if (file.listFiles() == null){
                    return;
                }
                List<File> fs = Arrays.asList(file.listFiles());

                Collections.sort(fs, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return (int) (o2.lastModified() - o1.lastModified());
                    }
                });

                final List<Log> logs = new ArrayList<>();
                for (File f : fs) {
                    logs.add(new Log(f, f.getName(), null,getModifiedTime(f)));
                }
                adapter.setFileList(logs);
            }
        });
    }


}
