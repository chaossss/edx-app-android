package com.xuemooc.edxapp.http.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

import com.xuemooc.edxapp.http.handler.WebHandler;
import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.image.cache.disk.BaseDiskCache;
import com.xuemooc.edxapp.image.cache.disk.DiskCache;
import com.xuemooc.edxapp.image.cache.memory.LruMemoryCache;
import com.xuemooc.edxapp.image.cache.memory.MemoryCache;
import com.xuemooc.edxapp.image.download.BaseImageDownloader;
import com.xuemooc.edxapp.image.download.ImageDownloader;

import java.io.IOException;

/**
 * Created by chaossss on 2015/9/26.
 */
public class LoadImageTask implements Runnable {
    private String url;
    private ILogin iLogin;
    private WebHandler handler;
    private DiskCache diskCache;
    private MemoryCache memoryCache;
    private ImageDownloader imageDownloader;

    public LoadImageTask(String url, ILogin iLogin) {
        this.url = url;
        this.iLogin = iLogin;
        handler = new WebHandler(this.iLogin);

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
                bitmap = imageDownloader.getStreamFromNet(url);
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
