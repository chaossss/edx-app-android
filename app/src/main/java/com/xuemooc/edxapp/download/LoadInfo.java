package com.example.chaos.downloadlibrary;

/**
 * Created by chaos on 2015/10/23.
 */
public class LoadInfo {
    private int fileSize;
    private int complete;
    private String url;

    public LoadInfo(int fileSize, int complete, String url) {
        this.fileSize = fileSize;
        this.complete = complete;
        this.url = url;
    }

    public LoadInfo() {
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getComplete() {
        return complete;
    }

    public String getUrl() {
        return url;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LoadInfo [fileSize=" + fileSize + ", complete=" + complete
                + ", urlstring=" + url + "]";
    }
}
