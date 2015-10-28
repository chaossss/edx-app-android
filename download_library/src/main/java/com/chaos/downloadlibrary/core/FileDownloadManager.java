package com.chaos.downloadlibrary.core;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.chaos.downloadlibrary.DownloadConst;
import com.chaos.downloadlibrary.http.response.OnDownloadInfoResponse;
import com.chaos.downloadlibrary.http.task.DownloadPretreatmentTask;
import com.chaos.downloadlibrary.http.task.DownloadTask;
import com.chaos.downloadlibrary.http.module.DAO;
import com.chaos.downloadlibrary.http.module.DownloadInfo;
import com.chaos.downloadlibrary.http.module.LoadInfo;
import com.chaos.downloadlibrary.util.DownloadHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chaos on 2015/10/24.
 */
public class FileDownloadManager implements DownloadManager, OnDownloadInfoResponse{
    private static final int THREAD_COUNT = 4;
    private static final int THREAD_POOL_SIZE = 2;

    private Handler UIHandler;
    private final Executor threadPool;
    private final Map<String, ExecutorService> downloadTasks;
    private volatile static FileDownloadManager fileDownloader;

    public static final String STORAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/UESTC_MOOC/Download";

    private Context context;
    private DownloadHandler downloadHandler;

    private FileDownloadManager(Context context, Handler UIHandler) {
        this.context = context;
        this.UIHandler = UIHandler;
        downloadTasks = new ConcurrentHashMap<>();
        downloadHandler = new DownloadHandler(this);
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static FileDownloadManager getInstance(Context context, Handler UIHandler){
        if(fileDownloader == null){
            fileDownloader = new FileDownloadManager(context, UIHandler);
        }

        return fileDownloader;
    }

    @Override
    public void receive(String urlStr) {
        if (isDownloadFirstTime(urlStr)) {
            DownloadPretreatmentTask downloadPretreatmentTask = new DownloadPretreatmentTask(urlStr, STORAGE_PATH + urlStr.hashCode(), downloadHandler);
            downloadTasks.put(urlStr, Executors.newFixedThreadPool(THREAD_COUNT));
            threadPool.execute(downloadPretreatmentTask);
        } else {
            List<DownloadInfo> downloadInfos = DAO.getInstance(context).getInfos(urlStr);

            int size = 0;
            int completeSize = 0;
            for(DownloadInfo downloadInfo : downloadInfos){
                completeSize += downloadInfo.getCompeleteSize();
                size += downloadInfo.getEndPos() - downloadInfo.getStartPos() + 1;
            }

            download(new LoadInfo(size, completeSize, urlStr), downloadInfos);
        }
    }

    @Override
    public void download(LoadInfo loadInfo, List<DownloadInfo> downloadInfos) {
        if(downloadTasks.size() == 0 || downloadTasks == null){
            return;
        }

        if(downloadInfos != null){
            for(DownloadInfo downloadInfo : downloadInfos){
                downloadTasks.get(loadInfo.getUrlstring()).execute(new DownloadTask(context, downloadInfo, UIHandler));
            }

//            Message msg = Message.obtain();
//            msg.obj = loadInfo;
//            msg.what = DownloadConst.DOWNLOAD_UPDATE_UI;
//            UIHandler.sendMessage(msg);
        }
    }

    private boolean isDownloadFirstTime(String urlStr){
        return DAO.getInstance(context).isHasInfo(urlStr);
    }

    @Override
    public void cancel(String urlStr) {
        ExecutorService canceledTask = downloadTasks.remove(urlStr);
        for(Runnable downloadTask : canceledTask.shutdownNow()){
            ((DownloadTask) downloadTask).pause();
        }
    }

    @Override
    public void onDownloadInfoResponse(Message msg) {
        switch(msg.what){
            case DownloadConst.DOWNLOAD_PRETREATMENT:
                int fileSize = msg.arg1;
                String urlStr = (String) msg.obj;
                int range = fileSize / THREAD_COUNT;

                List<DownloadInfo> downloadInfos = new ArrayList<>();
                for(int i = 0; i < THREAD_COUNT - 1; i++){
                    DownloadInfo downloadInfo = new DownloadInfo(i, i * range, (i + 1) * range - 1, 0, urlStr);
                    downloadInfos.add(downloadInfo);
                }
                downloadInfos.add(new DownloadInfo(THREAD_COUNT - 1, (THREAD_COUNT - 1) * range, fileSize - 1, 0, urlStr));
                DAO.getInstance(context).saveInfo(downloadInfos);

                download(new LoadInfo(fileSize, 0, urlStr), downloadInfos);
                break;
        }
    }
}