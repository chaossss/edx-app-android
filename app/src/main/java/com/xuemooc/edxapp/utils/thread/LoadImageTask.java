package com.xuemooc.edxapp.utils.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.utils.cache.disk.BaseDiskCache;
import com.xuemooc.edxapp.utils.cache.disk.DiskCache;
import com.xuemooc.edxapp.utils.cache.memory.LruMemoryCache;
import com.xuemooc.edxapp.utils.cache.memory.MemoryCache;
import com.xuemooc.edxapp.utils.download.BaseImageDownloader;
import com.xuemooc.edxapp.utils.download.ImageDownloader;

import java.io.IOException;

/**
 * Created by chaossss on 2015/9/26.
 */
public class LoadImageTask implements Runnable {
    private String url;
    private IWebMessage iWebMessage;
    private WebHandler handler;
    private DiskCache diskCache;
    private MemoryCache memoryCache;
    private ImageDownloader imageDownloader;

    public LoadImageTask(String url, IWebMessage iWebMessage) {
        this.url = url;
        this.iWebMessage = iWebMessage;
        handler = new WebHandler(this.iWebMessage);

        diskCache = new BaseDiskCache();
        memoryCache = new LruMemoryCache();
        imageDownloader = new BaseImageDownloader();
    }

    @Override
    public void run() {
        Bitmap bitmap = loadImageFromNative();

        if(bitmap == null){
            try {
                Log.v("image-load", "load from web");
                bitmap = imageDownloader.getStream(url);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        Message msg = Message.obtain();
        msg.obj = bitmap;
        msg.what = 6666;
        handler.sendMessage(msg);
    }

    private Bitmap loadImageFromNative(){
        Log.v("image-load", "load from memory");
        Bitmap bitmap = memoryCache.get(url);

        if(bitmap == null){
            Log.v("image-load", "load from disk");
            String path = diskCache.get(url).getAbsolutePath();
            bitmap = BitmapFactory.decodeFile(path);
        }

        return bitmap;
    }
}
