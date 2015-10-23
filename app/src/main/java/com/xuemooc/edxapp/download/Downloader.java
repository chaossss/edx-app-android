package com.example.chaos.downloadlibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaos on 2015/10/23.
 */
public class Downloader {
    private String urlStr;
    private String localFile;
    private int threadCount;
    private Handler mHandler;
    private int fileSize;
    private Context context;
    private List<DownloadInfo> infos;
    private static final int INIT = 1;
    private static final int DOWNLOADING = 2;
    private static final int PAUSE = 3;
    private int state = INIT;

    public Downloader(String urlStr, String localFile, int threadCount, Context context, Handler mHandler) {
        this.urlStr = urlStr;
        this.localFile = localFile;
        this.threadCount = threadCount;
        this.context = context;
        this.mHandler = mHandler;
    }

    public boolean isDownloading(){
        return state == DOWNLOADING;
    }

    public LoadInfo getDownloadersInfo(){
        if(isFirst(urlStr)){
            Log.v("TAG", "isFirst");
            init();

            int range = fileSize / threadCount;
            infos = new ArrayList<>();
            for(int i = 0; i < threadCount; i++){
                DownloadInfo info = new DownloadInfo(i, i * range, (i + 1) * range - 1, 0, urlStr);
                infos.add(info);
            }
            DownloadInfo info = new DownloadInfo(threadCount - 1, (threadCount - 1) * range, fileSize - 1, 0, urlStr);
            infos.add(info);
            Dao.getInstance(context).saveInfo(infos);
            LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlStr);
            return loadInfo;
        } else {
            infos = Dao.getInstance(context).getInfos(urlStr);
            Log.v("TAG", "not first size = " + infos.size());

            int size = 0;
            int completeSize = 0;
            for(DownloadInfo info : infos){
                completeSize += info.getCompleteSize();
                size += info.getEndPos() - info.getStartPos() + 1;
            }
            return new LoadInfo(size, completeSize, urlStr);
        }
    }

    private void init(){
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            fileSize = connection.getContentLength();

            File file = new File(localFile);
            if(!file.exists()){
                file.createNewFile();
            }

            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            connection.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isFirst(String url){
        return Dao.getInstance(context).isHasInfo(url);
    }

    public void download(){
        if(infos != null){
            if(state == DOWNLOADING){
                return;
            }

            state = DOWNLOADING;
            for(DownloadInfo info : infos){

            }
        }
    }

    public class MyThread extends Thread {
        private int threadId;
        private int startPos;
        private int endPos;
        private int completeSize;
        private String url;

        public MyThread(int threadId, int startPos, int endPos, int completeSize, String url) {
            this.threadId = threadId;
            this.startPos = startPos;
            this.endPos = endPos;
            this.completeSize = completeSize;
            this.url = url;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            InputStream is = null;

            try {
                URL url = new URL(urlStr);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Range", "bytes=" + (startPos + completeSize) + "-" + endPos);
                randomAccessFile = new RandomAccessFile(localFile, "rwd");
                randomAccessFile.seek(startPos + completeSize);
                is = connection.getInputStream();
                byte[] buffer = new byte[4096];
                int length = - 1;
                while((length = is.read(buffer)) != -1){
                    randomAccessFile.write(buffer, 0, length);
                    completeSize += length;
                    Dao.getInstance(context).updataInfos(threadId, completeSize, urlStr);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = urlStr;
                    msg.arg1 = length;
                    mHandler.sendMessage(msg);

                    if(startPos == PAUSE){
                        return;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void delete(String urlStr){
        Dao.getInstance(context).delete(urlStr);
    }

    public void pause(){
        state = PAUSE;
    }

    public void reset(){
        state = INIT;
    }
}
