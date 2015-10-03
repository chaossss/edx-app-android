package com.xuemooc.edxapp.utils.thread;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.utils.cache.disk.BaseDiskCache;
import com.xuemooc.edxapp.utils.cache.memory.LruMemoryCache;
import com.xuemooc.edxapp.utils.download.BaseImageDownloader;
import com.xuemooc.edxapp.utils.handler.WebHandler;

import java.io.IOException;

/**
 * Created by chaossss on 2015/9/26.
 */
public class LoadImageTask implements Runnable {
    private Message msg;
    private WebHandler handler;
    private IWebMessage iWebMessage;
    private BaseDiskCache diskCache;
    private LruMemoryCache memoryCache;
    private BaseImageDownloader imageDownloader;

    public LoadImageTask(Message msg, IWebMessage iWebMessage) {
        this.msg = msg;
        this.iWebMessage = iWebMessage;
        handler = new WebHandler(this.iWebMessage);

        diskCache = new BaseDiskCache();
        memoryCache = new LruMemoryCache();
        imageDownloader = new BaseImageDownloader();
    }

    @Override
    public void run() {
        String url = (String) msg.obj;
        Bitmap bitmap = loadImageFromNative(url);

        if(bitmap == null){
            try {
                Log.v("image-load", "load from disk - false");
                bitmap = imageDownloader.getStream(url);
                diskCache.save(url, bitmap);
                memoryCache.put(url, bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        Bundle b = new Bundle();
        Message temp = new Message();
        b.putString("url", url);
        temp.setData(b);
        temp.obj = bitmap;
        temp.what = msg.what;
        handler.sendMessage(temp);
    }

    private Bitmap loadImageFromNative(String url){
        Bitmap bitmap;

        if(memoryCache.isExist(url)){
            bitmap = memoryCache.get(url);
        } else {
            bitmap = null;
        }

//        bitmap = BitmapFactory.decodeFile(diskCache.getFilePath(url));

        return bitmap;
    }
}
