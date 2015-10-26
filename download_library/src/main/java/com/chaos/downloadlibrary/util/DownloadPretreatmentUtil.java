package com.chaos.downloadlibrary.util;

import android.content.Context;

import com.chaos.downloadlibrary.module.DAO;
import com.chaos.downloadlibrary.module.LoadInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chaos on 2015/10/26.
 */
public class DownloadPretreatmentUtil {
    private volatile static DownloadPretreatmentUtil downloadPretreatmentUtil;

    private String urlStr;
    private Context context;

    private DownloadPretreatmentUtil(String urlStr, Context context) {
        this.urlStr = urlStr;
        this.context = context;
    }

    public static DownloadPretreatmentUtil getInstance(String urlStr, Context context){
        if(downloadPretreatmentUtil == null){
            downloadPretreatmentUtil = new DownloadPretreatmentUtil(urlStr, context);
        }

        return downloadPretreatmentUtil;
    }

    public LoadInfo pretreatment(){
        if(isDownloadFirstTime()){

        }
    }

    private void initHttpConnect(){
        try {
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();

            File file = new File(localfile);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 本地访问文件
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDownloadFirstTime(){
        return DAO.getInstance(context).isHasInfo(urlStr);
    }
}
