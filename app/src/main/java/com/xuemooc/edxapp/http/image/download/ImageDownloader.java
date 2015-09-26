package com.xuemooc.edxapp.http.image.download;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by chaossss on 2015/9/24.
 */
public interface ImageDownloader {
    Bitmap getStream(String imageUri) throws IOException;
}
