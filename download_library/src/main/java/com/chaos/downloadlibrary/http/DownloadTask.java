package com.chaos.downloadlibrary.http;

/**
 * Created by chaos on 2015/10/24.
 */
public class DownloadTask implements Runnable {
    public static final int INIT = 1;
    public static final int DOWNLOAD = 2;
    public static final int PAUSE = 3;
    private static final int THREAD_COUNT = 4;

    private int state;
    private  String urlStr;

    public DownloadTask(String urlStr) {
        this.urlStr = urlStr;
    }

    @Override
    public void run() {

    }

    public void pause(){
        state = PAUSE;
    }
}
