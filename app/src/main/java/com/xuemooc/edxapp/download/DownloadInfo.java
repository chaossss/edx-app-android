package com.example.chaos.downloadlibrary;

/**
 * Created by chaos on 2015/10/23.
 */
public class DownloadInfo {
    private int endPos;
    private int startPos;
    private int threadId;
    private int completeSize;

    private String url;

    public DownloadInfo() {
    }

    public DownloadInfo(int endPos, int startPos, int threadId, int completeSize, String url) {
        this.endPos = endPos;
        this.startPos = startPos;
        this.threadId = threadId;
        this.completeSize = completeSize;
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

    public int getCompleteSize() {
        return completeSize;
    }

    public String getUrl() {
        return url;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public void setCompleteSize(int completeSize) {
        this.completeSize = completeSize;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DownloadInfo [threadId=" + threadId
                + ", startPos=" + startPos + ", endPos=" + endPos
                + ", compeleteSize=" + completeSize +"]";
    }
}
