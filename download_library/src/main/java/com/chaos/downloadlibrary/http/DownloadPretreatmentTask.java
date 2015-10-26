package com.chaos.downloadlibrary.http;

import android.content.Context;
import android.os.Message;

import com.chaos.downloadlibrary.DownloadConst;
import com.chaos.downloadlibrary.util.DownloadHandler;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chaos on 2015/10/26.
 */
public class DownloadPretreatmentTask implements Runnable {
    private String urlStr;
    private String filePath;
    private DownloadHandler downloadHandler;

    public DownloadPretreatmentTask(String urlStr, String filePath, DownloadHandler downloadHandler) {
        this.urlStr = urlStr;
        this.filePath = filePath;
        this.downloadHandler = downloadHandler;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");

            int fileSize = fileSize = connection.getContentLength();
            connection.disconnect();

            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();

            Message msg = Message.obtain();
            msg.what = DownloadConst.DOWNLOAD_PRETREATMENT;
            msg.arg1 = fileSize;
            msg.obj = urlStr;
            downloadHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
