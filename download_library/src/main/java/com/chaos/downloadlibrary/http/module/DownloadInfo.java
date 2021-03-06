package com.chaos.downloadlibrary.http.module;

/**
 * DownloadInfo model
 * Created by chaos on 2015/10/24.
 */
public class DownloadInfo {
    private int endPos;//结束点
    private int startPos;//开始点
    private int threadId;//下载器id
    private int compeleteSize;//完成度

    private String url;//下载器网络标识

    public DownloadInfo(int threadId, int startPos, int endPos,
                        int compeleteSize,String url) {
        this.threadId = threadId;
        this.startPos = startPos;
        this.endPos = endPos;
        this.compeleteSize = compeleteSize;
        this.url=url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getEndPos() {
        return endPos;
    }

    public int getStartPos() {
        return startPos;
    }

    public int getThreadId() {
        return threadId;
    }

    public int getCompeleteSize() {
        return compeleteSize;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "DownloadInfo [threadId=" + threadId
                + ", startPos=" + startPos + ", endPos=" + endPos
                + ", compeleteSize=" + compeleteSize +"]";
    }
}
