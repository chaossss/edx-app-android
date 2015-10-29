package com.chaos.downloadlibrary.core;

import java.util.List;

/**
 * Created by chaos on 2015/10/29.
 */
public class FileDownloadManager implements DownloadManager {
    @Override
    public String getFilePath(String url) {
        return null;
    }

    @Override
    public boolean isDownload(String urlStr) {
        return false;
    }

    @Override
    public void download(List<String> urlList) {

    }

    @Override
    public void cancelDownload(List<String> urlList) {

    }
}
