package com.chaos.downloadlibrary.http.download;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chaos.downloadlibrary.util.state.DownloadConst;
import com.chaos.downloadlibrary.util.database.DAO;
import com.chaos.downloadlibrary.http.module.DownloadInfo;
import com.chaos.downloadlibrary.http.module.LoadInfo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * FileDownloader class can be used to download file from net
 * Created by chaos on 2015/10/28.
 */
public class FileDownloader implements Downloader {
    private String urlStr;
    private String filePath;

    private int fileSize;
    private int threadCount;
    private int state = DownloadConst.DOWNLOAD_INIT;

    private Context context;
    private Handler handler;
    private List<DownloadInfo> downloadInfos;

    public FileDownloader(String urlStr, String filePath, Context context, Handler handler) {
        this(urlStr, filePath, 4, context, handler);
    }

    public FileDownloader(String urlStr, String filePath, int threadCount, Context context, Handler handler) {
        this.urlStr = urlStr;
        this.filePath = filePath;
        this.threadCount = threadCount;
        this.context = context;
        this.handler = handler;
    }

    public LoadInfo getDownloadInfo(){
        if (isFirst(urlStr)) {
            Log.v("TAG", "isFirst");

            init();

            int range = fileSize / threadCount;

            downloadInfos = new ArrayList<>();
            for (int i = 0; i < threadCount - 1; i++) {
                DownloadInfo info = new DownloadInfo(i, i * range, (i + 1)* range - 1, 0, urlStr);
                downloadInfos.add(info);
            }
            DownloadInfo info = new DownloadInfo(threadCount - 1,(threadCount - 1) * range, fileSize - 1, 0, urlStr);
            downloadInfos.add(info);

            //保存infos中的数据到数据库
            DAO.getInstance(context).saveInfo(downloadInfos);

            return new LoadInfo(fileSize, 0, urlStr);
        } else {
            //得到数据库中已有的urlstr的下载器的具体信息
            downloadInfos = DAO.getInstance(context).getInfos(urlStr);

            Log.v("TAG", "not isFirst size=" + downloadInfos.size());

            int size = 0;
            int completeSize = 0;

            for (DownloadInfo downloadInfo : downloadInfos) {
                completeSize += downloadInfo.getCompeleteSize();
                size += downloadInfo.getEndPos() - downloadInfo.getStartPos() + 1;
            }

            return new LoadInfo(size, completeSize, urlStr);
        }
    }

    private void init() {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();

            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 本地访问文件
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isFirst(String urlStr) {
        return DAO.getInstance(context).isHasInfo(urlStr);
    }

    public boolean isDownloading(){
        return state == DownloadConst.DOWNLOAD_DOWNLOADING;
    }

    @Override
    public void pause() {
        state = DownloadConst.DOWNLOAD_PAUSE;
    }

    @Override
    public void reset() {
        state = DownloadConst.DOWNLOAD_INIT;
    }

    @Override
    public void delete(String urlStr) {
        DAO.getInstance(context).delete(urlStr);
    }

    @Override
    public void download() {
        if (downloadInfos != null) {
            if (state == DownloadConst.DOWNLOAD_DOWNLOADING)
                return;

            state = DownloadConst.DOWNLOAD_DOWNLOADING;
            for (DownloadInfo info : downloadInfos) {
                Log.v("TAG", "startThread");
                Log.v("DATA", "getCompeleteSize" + info.getCompeleteSize());
                new MyThread(info.getThreadId(), info.getStartPos(),
                        info.getEndPos(), info.getCompeleteSize(),
                        info.getUrl()).start();
            }
        }
    }

    public class MyThread extends Thread {
        private int threadId;
        private int startPos;
        private int endPos;
        private int completeSize;
        private String urlStr;

        public MyThread(int threadId, int startPos, int endPos,
                        int completeSize, String urlStr) {
            this.threadId = threadId;
            this.startPos = startPos;
            this.endPos = endPos;
            this.completeSize = completeSize;
            this.urlStr = urlStr;
        }

        @Override
        public void run() {
            InputStream is;
            HttpURLConnection connection;
            RandomAccessFile randomAccessFile;

            try {
                Log.v("TAG", "runThread");
                URL url = new URL(urlStr);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");

                // 设置范围，格式为Range：bytes x-y;
                connection.setRequestProperty("Range", "bytes="+(startPos + completeSize) + "-" + endPos);

                randomAccessFile = new RandomAccessFile(filePath, "rwd");
                randomAccessFile.seek(startPos + completeSize);

                // 将要下载的文件写到保存在保存路径下的文件中
                int length;
                byte[] buffer = new byte[4096];
                is = connection.getInputStream();

                while ((length = is.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, length);
                    completeSize += length;

                    Log.v("DATA", "updateDB" + completeSize * 4);
                    // 更新数据库中的下载信息
                    DAO.getInstance(context).updataInfos(threadId, completeSize, urlStr);

                    // 用消息将下载信息传给进度条，对进度条进行更新
                    Message message = Message.obtain();
                    message.what = DownloadConst.DOWNLOAD_UPDATE_UI;
                    message.obj = urlStr;
                    message.arg1 = length;
                    Log.v("TAG", "updateUI");
                    handler.sendMessage(message);
                    if (state == DownloadConst.DOWNLOAD_PAUSE) {
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
