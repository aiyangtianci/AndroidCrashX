package com.aiyang.android_crashx.CrashLog;

import java.io.File;

public class Log {
    public File file;
    public String title;
    public String content;
    public String time;

    public Log(File file, String title, String content, String time) {
        this.file = file;
        this.title = title;
        this.content = content;
        this.time = time;
    }
}
