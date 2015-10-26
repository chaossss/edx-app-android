package com.chaos.downloadlibrary.util;

import android.os.Handler;
import android.os.Message;

import com.chaos.downloadlibrary.OnDownloadInfoResponse;

/**
 * Created by chaos on 2015/10/26.
 */
public class DownloadHandler extends Handler{
    private OnDownloadInfoResponse onDownloadInfoResponse;

    public DownloadHandler(OnDownloadInfoResponse onDownloadInfoResponse) {
        this.onDownloadInfoResponse = onDownloadInfoResponse;
    }

    @Override
    public void handleMessage(Message msg) {
        onDownloadInfoResponse.onDownloadInfoResponse(msg);
    }
}
