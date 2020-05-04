package com.aiyang.android_crashx.CrashLog;

import java.io.File;

public class Log {
    File file;
    String title;
    String content;

    public Log(File file, String title, String content) {
        this.file = file;
        this.title = title;
        this.content = content;
    }
}
