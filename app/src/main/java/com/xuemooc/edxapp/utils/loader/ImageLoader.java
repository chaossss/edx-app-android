package com.xuemooc.edxapp.utils.loader;

import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.utils.thread.LoadImageTask;

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
     * @param msg
     * @param iWebMessage
     */
    public void load(Message msg, IWebMessage iWebMessage){
        new Thread(new LoadImageTask(msg, iWebMessage)).start();
    }
}
