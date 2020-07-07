package com.aiyang.crashx.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.aiyang.crashx.inter.ICrashLogFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 日志文件
 * @author aiyang
 */
public class LogFile {

    public static void saveCrashLog(Context context, Throwable throwable) {
        Map<String, String> map = collectDeviceInfo(context);
        saveCrashInfo2File(context, throwable, map);
    }


    private static Map<String, String> collectDeviceInfo(Context ctx) {
        Map<String, String> infos = new TreeMap<>();
        try {

            infos.put("systemVersion", Build.VERSION.RELEASE);
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
        return infos;
    }

    /**
     * sava files
     * @param context
     * @param ex
     * @param infos
     */
    private static void saveCrashInfo2File(Context context, Throwable ex, Map<String, String> infos) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        try {
            long timestamp = System.currentTimeMillis();
//            String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
//            String fileName =time + "-" + timestamp + ".log";
            String fileName = ex.toString()+timestamp+ ".log";
            String cachePath = crashLogDir(context);
            File dir = new File(cachePath);
            dir.mkdirs();
            FileOutputStream fos = new FileOutputStream(cachePath + fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
            if (iCrashLogFileListener!=null){
                iCrashLogFileListener.OnNewCrashListener(fileName,getModifiedTime(dir));
            }
        } catch (Exception e) {
        }
    }
    private static ICrashLogFile iCrashLogFileListener;
    public static void addCrashLogFileListener(ICrashLogFile crashLogFileListener){
        iCrashLogFileListener = crashLogFileListener;
    }

    /**
     * get files
     * @param context
     * @return
     */
    public static List<File> getCrashFiles(Context context){
        List<File> fs = new ArrayList<>();
        String dir = LogFile.crashLogDir(context);
        if (dir == null) {
            return fs;
        }
        File file = new File(dir);
        if (file.listFiles() == null){
            return fs;
        }
        fs = Arrays.asList(file.listFiles());
        return fs;
    }
    /**
     * delete files
     * @return
     */
    public static boolean deleteDirectory(Context context) {
        String dir = LogFile.crashLogDir(context);
        if (dir == null){
            return false;
        }
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;//filePath添加文件分隔符
        }
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                if (!deleteFile(files[i].getAbsolutePath())) {
                    return false;
                }
            }
        }
        //删除当前空目录
        return dirFile.delete();
    }
    /**
     * file path
     * @param context
     * @return
     */
    public static String crashLogDir(Context context) {
        return context.getCacheDir().getPath() + File.separator + "crash" + File
                .separator;
    }

    /**
     * file time
     * @param f
     * @return
     */
    public static String getModifiedTime(File f){
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm:ss");
        cal.setTimeInMillis(time);
        return formatter.format(cal.getTime());
    }

    /**
     * delete file
     * @param   filePath
     * @return
     */
    protected static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
