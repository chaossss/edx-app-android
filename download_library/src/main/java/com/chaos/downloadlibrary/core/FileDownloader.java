package com.chaos.downloadlibrary.core;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.chaos.downloadlibrary.http.DownloadTask;
import com.chaos.downloadlibrary.module.DAO;
import com.chaos.downloadlibrary.module.DownloadInfo;
import com.chaos.downloadlibrary.module.LoadInfo;

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
public class FileDownloader implements Downloader {
    private static final int THREAD_COUNT = 4;
    private static final int THREAD_POOL_SIZE = 2;

    private final Executor threadPool;
    private final Map<String, Runnable> tasks;
    private volatile static FileDownloader fileDownloader;

    private static final String STORAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/UESTC_MOOC/Download";

    private Context context;

    private int fileSize;

    private List<DownloadInfo> infos;

    private FileDownloader(Context context) {
        this.context = context;
        tasks = new ConcurrentHashMap<>();
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
        tasks.put(urlStr, new DownloadTask(urlStr));
        download(urlStr);
    }

    @Override
    public void download(String urlStr) {
        if(infos != null){
            for(DownloadInfo downloadInfo : infos){

            }
        }
        threadPool.execute(tasks.get(urlStr));
    }

    public LoadInfo getLoadInfo(String urlStr){
        if(isDownloadFirstTime(urlStr)) {
            Log.v("TAG", "isDownloadFirstTime");
            init(urlStr);
            int range = fileSize / THREAD_COUNT;
            infos = new ArrayList<>();

            for(int i = 0; i < THREAD_COUNT - 1; i++){
                DownloadInfo downloadInfo = new DownloadInfo(i, i * range, (i + 1) * range - 1, 0, urlStr);
                infos.add(downloadInfo);
            }

            DownloadInfo downloadInfo = new DownloadInfo(THREAD_COUNT - 1, (THREAD_COUNT - 1) * range, fileSize -1, 0, urlStr);
            infos.add(downloadInfo);

            DAO.getInstance(context).saveInfo(infos);
            return new LoadInfo(fileSize, 0, urlStr);
        } else {
            infos = DAO.getInstance(context).getInfos(urlStr);
            Log.v("TAG", "not isFirst size = " + infos.size());

            int size = 0;
            int completeSize = 0;
            for(DownloadInfo info : infos) {
                completeSize += info.getCompeleteSize();
                size += info.getEndPos() - info.getStartPos() + 1;
            }

            return new LoadInfo(size, completeSize, urlStr);
        }
    }

    private void init(String urlStr){
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();

            File file = new File(STORAGE_PATH);
            if(!file.exists()){
                file.createNewFile();
            }

            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();
            connection.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isDownloadFirstTime(String urlStr){
        return DAO.getInstance(context).isHasInfo(urlStr);
    }

    @Override
    public void cancel(String urlStr) {
        DownloadTask task = (DownloadTask)tasks.get(urlStr);
        task.pause();
    }
}
