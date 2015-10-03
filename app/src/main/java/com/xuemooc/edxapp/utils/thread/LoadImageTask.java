package com.xuemooc.edxapp.utils.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
                Log.v("image-load", "load from native - false");
                bitmap = imageDownloader.getStream(url);
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

        try{
            diskCache.save(url, bitmap);
        } catch (IOException e){
            Log.v("image-cache", "disk cache error!");
        }
        memoryCache.put(url, bitmap);
    }

    private Bitmap loadImageFromNative(String url){
        Bitmap bitmap;

        bitmap = memoryCache.get(url);

        if(bitmap == null){
            bitmap = BitmapFactory.decodeFile(diskCache.getFilePath(url));
        }

        return bitmap;
    }
}
