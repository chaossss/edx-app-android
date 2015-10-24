package com.chaos.imageloader.core;

import android.os.Message;

/**
 * Loader used to load image
 *
 * Created by chaossss on 2015/9/24.
 */
public class ImageLoader{
    private volatile static ImageLoader imageLoader;

    private ImageLoader() {
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

    /**
     * Load Img by specified info, and update UI by IWebMessage callback
     * @param imgLoadTask
     */
    public void load(Runnable imgLoadTask){
        new Thread(imgLoadTask).start();
    }
}
