package com.chaos.downloadlibrary.core;

import android.os.Environment;

import com.chaos.downloadlibrary.http.DownloadTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chaos on 2015/10/24.
 */
public class FileDownloader implements Downloader {
    private static final int THREAD_POOL_SIZE = 2;

    private final Executor threadPool;
    private final Map<String, Runnable> tasks;
    private volatile static FileDownloader fileDownloader;

    private static final String STORAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/UESTC_MOOC/Download";

    private FileDownloader() {
        tasks = new ConcurrentHashMap<>();
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static FileDownloader getInstance(){
        if(fileDownloader == null){
            fileDownloader = new FileDownloader();
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
        threadPool.execute(tasks.get(urlStr));
    }

    @Override
    public void cancel(String urlStr) {
        DownloadTask task = (DownloadTask)tasks.get(urlStr);
        task.pause();
    }
}
