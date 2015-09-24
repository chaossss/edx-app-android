package com.xuemooc.edxapp.image.download;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chaossss on 2015/9/24.
 */
public class BaseImageDownloader implements ImageDownloader {
    private static final int MAX_REDIRECT_COUNT = 5;
    private static final int BUFFER_SIZE = 32 * 1024;
    private static final int HTTP_READ_TIMEOUT = 20 * 1000;
    private static final int HTTP_CONNECT_TIMEOUT = 5 * 1000;

    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    private static final String CONTENT_CONTACTS_URI_PREFIX = "content://com.android.contacts/";

    private final Context context;

    public BaseImageDownloader(Context context) {
        this.context = context;
    }

    @Override
    public InputStream getStream(String imageUri) throws IOException {
        return null;
    }
}
