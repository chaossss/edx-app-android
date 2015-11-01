package com.chaos.downloadlibrary.core;

import android.content.Context;

import com.chaos.downloadlibrary.http.download.Downloader;
import com.chaos.downloadlibrary.http.download.FileDownloader;
import com.chaos.downloadlibrary.util.database.DAO;
import com.chaos.downloadlibrary.util.file.FileUtil;

import java.util.List;

/**
 * Created by chaos on 2015/10/29.
 */
public class FileDownloadManager implements DownloadManager {
    private volatile static FileDownloadManager fileDownloadManager;

    private Context context;

    private DAO dao;
    private FileUtil fileUtil;
    private Downloader fileDownloader;

    private FileDownloadManager(Context context) {
        this.context = context;

        dao = DAO.getInstance(context);
        fileUtil = new FileUtil();
    }

    public FileDownloadManager getInstance(Context context){
        if(fileDownloadManager == null){
            fileDownloadManager = new FileDownloadManager(context);
        }

        return fileDownloadManager;
    }

    @Override
    public String getFilePath(String url) {
        return null;
    }

    @Override
    public boolean isDownload(String urlStr) {
        return dao.isHasInfo(urlStr);
    }

    @Override
    public void download(List<String> urlList) {

    }

    @Override
    public void cancelDownload(List<String> urlList) {

    }
}
