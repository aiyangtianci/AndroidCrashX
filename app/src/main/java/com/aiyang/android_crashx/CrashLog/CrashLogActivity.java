package com.aiyang.android_crashx.CrashLog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiyang.android_crashx.R;
import com.aiyang.crashx.util.LogFile;
import com.aiyang.crashx.util.Utils;

import java.io.File;
import java.util.ArrayList;
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
    private  List<Log> logs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_log);
        setTitle("LogCrash 记录");
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
                List<File> fs = LogFile.getCrashFiles(getBaseContext());
                Collections.sort(fs, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return (int) (o2.lastModified() - o1.lastModified());
                    }
                });
                logs = new ArrayList<>();
                for (File f : fs) {
                    logs.add(new Log(f, f.getName(), null,getModifiedTime(f)));
                }
                adapter.setFileList(logs);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_log:
                //清除
                Utils.showSimpleDialog(this, "清空确认", "点击确认，将彻底删除所有数据内容，并且无法恢复。", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LogFile.deleteDirectory(getBaseContext());
                        logs.clear();
                        adapter.notifyDataSetChanged();
                    }
                });
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.action_log).setTitle("清空记录    ");
        return true;
    }

}
