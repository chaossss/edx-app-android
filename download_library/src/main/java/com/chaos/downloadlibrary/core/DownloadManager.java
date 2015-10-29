package com.chaos.downloadlibrary.core;

import java.util.List;

/**
 * Created by chaos on 2015/10/29.
 */
public interface DownloadManager {
    String getFilePath(String url);
    boolean isDownload(String urlStr);
    void download(List<String> urlList);
    void cancelDownload(List<String> urlList);
}
