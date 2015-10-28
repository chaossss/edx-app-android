package com.chaos.downloadlibrary.core;

import com.chaos.downloadlibrary.http.module.DownloadInfo;
import com.chaos.downloadlibrary.http.module.LoadInfo;

import java.util.List;

/**
 * Created by chaos on 2015/10/24.
 */
public interface DownloadManager {
    void cancel(String urlStr);
    void receive(String urlStr);
    void download(LoadInfo loadInfo, List<DownloadInfo> downloadInfos);
}
