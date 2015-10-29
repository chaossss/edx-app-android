package com.chaos.downloadlibrary.http.module;

/**
 * LoadInfo model
 * Created by chaos on 2015/10/24.
 */
public class LoadInfo {
    public int fileSize;//文件大小
    private int complete;//完成度
    private String urlstring;//下载器标识

    public LoadInfo(int fileSize, int complete, String urlstring) {
        this.fileSize = fileSize;
        this.complete = complete;
        this.urlstring = urlstring;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getComplete() {
        return complete;
    }

    public String getUrlstring() {
        return urlstring;
    }

    @Override
    public String toString() {
        return "LoadInfo [fileSize=" + fileSize + ", complete=" + complete
                + ", urlstring=" + urlstring + "]";
    }
}
