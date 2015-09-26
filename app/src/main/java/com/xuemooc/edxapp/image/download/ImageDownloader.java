package com.xuemooc.edxapp.image.download;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by chaossss on 2015/9/24.
 */
public interface ImageDownloader {
    Bitmap getStreamFromNet(String imageUri) throws IOException;
}
