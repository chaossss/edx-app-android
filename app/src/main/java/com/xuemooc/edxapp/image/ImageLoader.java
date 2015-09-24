package com.xuemooc.edxapp.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xuemooc.edxapp.image.cache.disk.BaseDiskCache;
import com.xuemooc.edxapp.image.cache.disk.DiskCache;
import com.xuemooc.edxapp.image.cache.memory.LruMemoryCache;
import com.xuemooc.edxapp.image.cache.memory.MemoryCache;

/**
 * Created by chaossss on 2015/9/24.
 */
public class ImageLoader {
    private volatile static ImageLoader imageLoader;

    private DiskCache diskCache;
    private MemoryCache memoryCache;

    private ImageLoader() {
        diskCache = new BaseDiskCache();
        memoryCache = new LruMemoryCache();
    }

    public static ImageLoader getImageLoader(){
        if(imageLoader == null){
            synchronized (ImageLoader.class){
                if(imageLoader == null){
                    imageLoader = new ImageLoader();
                }
            }
        }

        return imageLoader;
    }

    public Bitmap load(String imageUri){
        Bitmap bitmap = memoryCache.get(imageUri);

        if(bitmap == null){
            String path = diskCache.get(imageUri).getAbsolutePath();
            bitmap = BitmapFactory.decodeFile(path);

            if(bitmap == null){

            }
        }

        return bitmap;
    }
}
