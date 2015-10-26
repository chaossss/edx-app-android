package com.chaos.downloadlibrary.core;

import com.chaos.downloadlibrary.module.LoadInfo;

/**
 * Created by chaos on 2015/10/24.
 */
public interface Downloader {
    void cancel(String urlStr);
    void receive(String urlStr);
    void download(LoadInfo loadInfo);
}
