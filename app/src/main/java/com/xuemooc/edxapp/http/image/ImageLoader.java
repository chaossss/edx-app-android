package com.xuemooc.edxapp.http.image;

import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.utils.thread.LoadImageTask;

/**
 * Created by chaossss on 2015/9/24.
 */
public class ImageLoader{
    private volatile static ImageLoader imageLoader;

    private IWebMessage iWebMessage;

    private ImageLoader(IWebMessage iWebMessage) {
        this.iWebMessage = iWebMessage;
    }

    public static ImageLoader getImageLoader(IWebMessage iWebMessage){
        if(imageLoader == null){
            synchronized (ImageLoader.class){
                if(imageLoader == null){
                    imageLoader = new ImageLoader(iWebMessage);
                }
            }
        }

        return imageLoader;
    }

    public void load(String imageUri){
        new Thread(new LoadImageTask(imageUri, iWebMessage)).start();
    }
}
