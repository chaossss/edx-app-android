package com.chaos.downloadlibrary.core;

/**
 * Created by chaos on 2015/10/24.
 */
public interface Downloader {
    void receive(String urlStr);
    void download(String urlStr);
    void cancel(String urlStr);
}
