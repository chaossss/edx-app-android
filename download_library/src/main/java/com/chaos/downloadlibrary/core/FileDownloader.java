package com.chaos.downloadlibrary.core;

import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import com.chaos.downloadlibrary.DownloadConst;
import com.chaos.downloadlibrary.OnDownloadInfoResponse;
import com.chaos.downloadlibrary.http.DownloadPretreatmentTask;
import com.chaos.downloadlibrary.http.DownloadTask;
import com.chaos.downloadlibrary.module.DAO;
import com.chaos.downloadlibrary.module.DownloadInfo;
import com.chaos.downloadlibrary.module.LoadInfo;
import com.chaos.downloadlibrary.util.DownloadHandler;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chaos on 2015/10/24.
 */
public class FileDownloader implements Downloader, OnDownloadInfoResponse{
    private static final int THREAD_COUNT = 4;
    private static final int THREAD_POOL_SIZE = 2;

    private final Executor threadPool;
    private List<DownloadInfo> downloadInfos;
    private final Map<String, Runnable> pretreamentTasks;
    private volatile static FileDownloader fileDownloader;

    private static final String STORAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/UESTC_MOOC/Download";

    private Context context;
    private DownloadHandler downloadHandler;

    private int fileSize;

    private FileDownloader(Context context) {
        this.context = context;
        downloadHandler = new DownloadHandler(this);
        pretreamentTasks = new ConcurrentHashMap<>();
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static FileDownloader getInstance(Context context){
        if(fileDownloader == null){
            fileDownloader = new FileDownloader(context);
        }

        return fileDownloader;
    }

    @Override
    public void receive(String urlStr) {
        if (isDownloadFirstTime(urlStr)) {
            DownloadPretreatmentTask downloadPretreatmentTask = new DownloadPretreatmentTask(urlStr, STORAGE_PATH + urlStr, downloadHandler);
            pretreamentTasks.put(urlStr, downloadPretreatmentTask);
            threadPool.execute(downloadPretreatmentTask);
        } else {
            downloadInfos = DAO.getInstance(context).getInfos(urlStr);

            int size = 0;
            int completeSize = 0;
            for(DownloadInfo downloadInfo : downloadInfos){
                completeSize += downloadInfo.getCompeleteSize();
                size += downloadInfo.getEndPos() - downloadInfo.getStartPos() + 1;
            }

            download(new LoadInfo(size, completeSize, urlStr));
        }
    }

    @Override
    public void download(LoadInfo loadInfo) {
        if(downloadInfos != null){
            for(DownloadInfo downloadInfo : downloadInfos){

            }
        }
        threadPool.execute(pretreamentTasks.get(loadInfo.getUrlstring()));
    }

    private boolean isDownloadFirstTime(String urlStr){
        return DAO.getInstance(context).isHasInfo(urlStr);
    }

    @Override
    public void cancel(String urlStr) {
    }

    @Override
    public void onDownloadInfoResponse(Message msg) {
        switch(msg.what){
            case DownloadConst.DOWNLOAD_PRETREATMENT:
                String urlStr = (String) msg.obj;
                int range = msg.arg1 / THREAD_COUNT;

                downloadInfos = new ArrayList<>();
                for(int i = 0; i < THREAD_COUNT - 1; i++){
                    DownloadInfo downloadInfo = new DownloadInfo(i, i * range, (i + 1) * range - 1, 0, urlStr);
                    downloadInfos.add(downloadInfo);
                }
                downloadInfos.add(new DownloadInfo(THREAD_COUNT - 1, (THREAD_COUNT - 1) * range, fileSize - 1, 0, urlStr));
                DAO.getInstance(context).saveInfo(downloadInfos);

                download(new LoadInfo(fileSize, 0, urlStr));
                break;
        }
    }
}
