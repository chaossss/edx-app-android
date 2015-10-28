package com.chaos.downloadlibrary.http.task;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.chaos.downloadlibrary.DownloadConst;
import com.chaos.downloadlibrary.core.FileDownloadManager;
import com.chaos.downloadlibrary.http.module.DAO;
import com.chaos.downloadlibrary.http.module.DownloadInfo;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chaos on 2015/10/24.
 */
public class DownloadTask implements Runnable {
    private int state;
    private int endPos;
    private int startPos;
    private int threadId;
    private int compeleteSize;

    private String urlStr;

    private Context context;
    private Handler UIHandler;

    public DownloadTask(Context context, DownloadInfo downloadInfo, Handler UIHandler) {
        state = DownloadConst.DOWNLOAD_INIT;

        this.context = context;
        this.UIHandler = UIHandler;
        this.urlStr = downloadInfo.getUrl();
        this.endPos = downloadInfo.getEndPos();
        this.startPos = downloadInfo.getStartPos();
        this.threadId = downloadInfo.getThreadId();
        this.compeleteSize = downloadInfo.getCompeleteSize();
    }

    @Override
    public void run() {
        InputStream is;
        HttpURLConnection connection;
        RandomAccessFile randomAccessFile;
        state = DownloadConst.DOWNLOAD_DOWNLOADING;

        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            // 设置范围，格式为Range：bytes x-y;
            connection.setRequestProperty("Range", "bytes="+(startPos + compeleteSize) + "-" + endPos);

            randomAccessFile = new RandomAccessFile(FileDownloadManager.STORAGE_PATH + urlStr.hashCode(), "rwd");
            randomAccessFile.seek(startPos + compeleteSize);

            // 将要下载的文件写到保存在保存路径下的文件中
            is = connection.getInputStream();
            byte[] buffer = new byte[4096];
            int length = -1;

            while ((length = is.read(buffer)) != -1) {
                randomAccessFile.write(buffer, 0, length);
                compeleteSize += length;

                // 更新数据库中的下载信息
                DAO.getInstance(context).updataInfos(threadId, compeleteSize, urlStr);

                // 用消息将下载信息传给进度条，对进度条进行更新
                Message message = Message.obtain();
                message.what = DownloadConst.DOWNLOAD_UPDATE_UI;
                message.obj = urlStr;
                message.arg1 = compeleteSize;
                UIHandler.sendMessage(message);

                if (state == DownloadConst.DOWNLOAD_PAUSE) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        state = DownloadConst.DOWNLOAD_PAUSE;
    }
}
