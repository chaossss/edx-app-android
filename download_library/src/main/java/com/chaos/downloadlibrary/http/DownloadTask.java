package com.chaos.downloadlibrary.http;

/**
 * Created by chaos on 2015/10/24.
 */
public class DownloadTask implements Runnable {
    private  String urlStr;

    public DownloadTask(String urlStr) {
        this.urlStr = urlStr;
    }

    @Override
    public void run() {

    }
}
