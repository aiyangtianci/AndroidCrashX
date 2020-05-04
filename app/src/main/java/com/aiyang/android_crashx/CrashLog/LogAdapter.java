package com.aiyang.android_crashx.CrashLog;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aiyang.android_crashx.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogVH> {
    private Handler fileReadHandler;
    private List<Log> logs;
    private Context mContext;

    public LogAdapter(Context mContext,Handler fileReadHandler) {
        this.mContext = mContext;
        this.fileReadHandler =fileReadHandler;
    }

    @NonNull
    @Override
    public LogVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LogVH(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull LogVH holder, int position) {
        holder.title.setTag(position);
        Log log = getData(position);
        holder.copy.setTag(log.content);
        holder.title.setText(log.title);
        if (log.content == null) {
            holder.content.setVisibility(View.GONE);
            holder.copy.setVisibility(View.INVISIBLE);
        } else {
            holder.content.setText(log.content);
            holder.content.setVisibility(View.VISIBLE);
            holder.copy.setVisibility(View.VISIBLE);
        }
    }

    protected Log getData(int position) {
        return logs.get(position);
    }

    @Override
    public int getItemCount() {
        return logs == null ? 0 : logs.size();
    }

    public void setFileList(List<Log> fs) {
        this.logs = fs;
        notifyDataSetChanged();
    }


    class LogVH extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        TextView copy;

        public LogVH(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.item_crash_log,parent , false));
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            copy = itemView.findViewById(R.id.copy);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((int) v.getTag());
                    if (logs.get(position).content == null) {
                        readFileContent(logs.get(position).file);
                    }
                }
            });
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String log = ((String) v.getTag());
                    ClipboardManager cmb = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(log);
                    Toast.makeText(v.getContext(), "已经复制到粘贴板", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /**
     * 展开日志内容
     * @param file
     */
    private void readFileContent(final File file) {

        fileReadHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    String content = read(file);
                    update(file, content);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 读写
     * @param file
     * @return
     * @throws Exception
     */
    private String read(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;

        StringBuilder builder = new StringBuilder((int) file.length());
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();

    }

    /**
     * 更改Ui
     * @param file
     * @param content
     */
    private void update(final File file, final String content) {
        if (content == null) {
            return;
        }
        for (Log log : logs) {
            if (log.file == file) {
                log.content = content;
                notifyDataSetChanged();
                return;
            }
        }
    }
}
