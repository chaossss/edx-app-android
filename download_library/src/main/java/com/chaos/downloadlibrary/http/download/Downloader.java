package com.chaos.downloadlibrary.http.download;

/**
 * Abstract class that defines what a downloader can or should do
 * Created by chaos on 2015/10/28.
 */
public interface Downloader {
    void pause();
    void reset();
    void download();
    void delete(String url);
}
