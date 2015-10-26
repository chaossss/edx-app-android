package com.chaos.downloadlibrary.http;

import android.content.Context;

/**
 * Created by chaos on 2015/10/26.
 */
public class DownloadPretreatmentTask implements Runnable {
    private String urlStr;
    private Context context;

    public DownloadPretreatmentTask(String urlStr, Context context) {
        this.urlStr = urlStr;
        this.context = context;
    }

    @Override
    public void run() {

    }
}
