package com.xuemooc.edxapp.utils.thread;

import android.os.Message;
import android.util.Log;

import com.chaos.downloadlibrary.util.state.DownloadConst;
import com.chaos.downloadlibrary.http.download.Downloader;
import com.chaos.downloadlibrary.http.download.FileDownloader;
import com.chaos.downloadlibrary.http.module.LoadInfo;
import com.xuemooc.edxapp.utils.handler.WebHandler;

/**
 * Created by chaos on 2015/10/26.
 */
public class DownloadPretreatmentTask implements Runnable {
    private WebHandler handler;
    private FileDownloader downloader;

    public DownloadPretreatmentTask(Downloader downloader, WebHandler handler) {
        this.handler = handler;
        this.downloader = (FileDownloader)downloader;
    }

    @Override
    public void run() {
        Log.v("TAG", "startThread");
        if(downloader.isDownloading()){
            return;
        }

        LoadInfo loadInfo = downloader.getDownloadInfo();
        if(loadInfo!=null){
            Message msg = Message.obtain();
            msg.obj = loadInfo;
            msg.what = DownloadConst.DOWNLOAD_INIT;
            handler.sendMessage(msg);

            Log.v("TAG", "download");
            downloader.download();
        }
    }
}
