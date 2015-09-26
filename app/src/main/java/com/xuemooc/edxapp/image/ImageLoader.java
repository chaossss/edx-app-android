package com.xuemooc.edxapp.image;

import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.http.thread.LoadImageTask;

/**
 * Created by chaossss on 2015/9/24.
 */
public class ImageLoader{
    private volatile static ImageLoader imageLoader;

    private ILogin iLogin;

    private ImageLoader(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    public static ImageLoader getImageLoader(ILogin iLogin){
        if(imageLoader == null){
            synchronized (ImageLoader.class){
                if(imageLoader == null){
                    imageLoader = new ImageLoader(iLogin);
                }
            }
        }

        return imageLoader;
    }

    public void load(String imageUri){
        new Thread(new LoadImageTask(imageUri, iLogin)).start();
    }
}
