package com.xuemooc.edxapp.http.image.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.xuemooc.edxapp.http.image.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chaossss on 2015/9/24.
 */
public class BaseImageDownloader implements ImageDownloader {
    private static final int MAX_REDIRECT_COUNT = 5;
    private static final int BUFFER_SIZE = 32 * 1024;
    private static final int HTTP_READ_TIMEOUT = 20 * 1000;
    private static final int HTTP_CONNECT_TIMEOUT = 5 * 1000;

    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    @Override
    public Bitmap getStream(String imageUri) throws IOException {
        HttpURLConnection conn = createConnection(imageUri);

        int redirectCount = 0;
        while(conn.getResponseCode() / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT){
            conn = createConnection(conn.getHeaderField("Location"));
            redirectCount++;
        }

        Bitmap bitmap;
        InputStream imageStream;
        try {
            imageStream = conn.getInputStream();
        } catch (IOException e){
            IOUtils.readAndCloseStream(conn.getErrorStream());
            throw e;
        }

        if(!shouldBeProcessed(conn)){
            IOUtils.closeSliently(imageStream);
            throw new IOException("Image request failed with response code" + conn.getResponseCode());
        }

        bitmap = BitmapFactory.decodeStream(new ContentLengthInputStream(new BufferedInputStream(imageStream, BUFFER_SIZE), conn.getContentLength()));
        return bitmap;
    }

    private HttpURLConnection createConnection(String imageUri) throws IOException{
        String encodeUrl = Uri.encode(imageUri, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(encodeUrl).openConnection();
        conn.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
        conn.setReadTimeout(HTTP_READ_TIMEOUT);
        return conn;
    }

    private boolean shouldBeProcessed(HttpURLConnection conn) throws IOException{
        return conn.getResponseCode() == 200;
    }
}
