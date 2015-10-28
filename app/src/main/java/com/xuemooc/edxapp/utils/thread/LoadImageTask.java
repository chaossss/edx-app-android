package com.xuemooc.edxapp.utils.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.chaos.imageloader.cache.disk.BaseDiskCache;
import com.chaos.imageloader.cache.disk.DiskCache;
import com.chaos.imageloader.cache.memory.LruMemoryCache;
import com.chaos.imageloader.cache.memory.MemoryCache;
import com.chaos.imageloader.core.download.BaseImageDownloader;
import com.chaos.imageloader.core.download.ImageDownloader;
import com.xuemooc.edxapp.utils.interfaces.WebCommunication;
import com.xuemooc.edxapp.utils.handler.WebHandler;

import java.io.IOException;

/**
 * An async task used to process image's loading process
 *
 * Created by chaossss on 2015/9/26.
 */
public class LoadImageTask implements Runnable {
    private Message msg;
    private WebHandler handler;
    private WebCommunication webCommunication;
    private DiskCache diskCache;
    private MemoryCache memoryCache;
    private ImageDownloader imageDownloader;

    public LoadImageTask(Message msg, WebCommunication webCommunication) {
        this.msg = msg;
        this.webCommunication = webCommunication;

        initParams();
    }

    private void initParams(){
        handler = new WebHandler(webCommunication);

        diskCache = new BaseDiskCache();
        memoryCache = new LruMemoryCache();
        imageDownloader = new BaseImageDownloader();
    }

    @Override
    public void run() {
        String url = (String) msg.obj;
        Bitmap bitmap = loadImage(url);

        Bundle b = new Bundle();
        b.putString("url", url);

        Message temp = new Message();
        temp.setData(b);
        temp.obj = bitmap;
        temp.what = msg.what;
        handler.sendMessage(temp);
    }

    private Bitmap loadImage(String url){
        Bitmap bitmap = loadImageFromNative(url);

        return bitmap == null ? loadImageFromWeb(url) : bitmap;
    }

    private Bitmap loadImageFromWeb(String url){
        Log.v("image-load", "load from web");
        Bitmap bitmap = null;
        try {
            bitmap = imageDownloader.getStream(url);

            diskCache.save(url, bitmap);
            memoryCache.put(url, bitmap);
        } catch (IOException e){
            e.printStackTrace();
        }

        return bitmap;
    }

    private Bitmap loadImageFromNative(String url){
        Log.v("image-load", "load from native");
        Bitmap bitmap;

        bitmap = memoryCache.get(url);

        if(bitmap == null){
            bitmap = BitmapFactory.decodeFile(diskCache.getFile(url).getAbsolutePath());
        }

        if(bitmap != null){
            Log.v("image-load", "load from native - true");
        }

        return bitmap;
    }
}
