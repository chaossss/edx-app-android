package com.xuemooc.edxapp.utils.cache.disk;

import android.graphics.Bitmap;
import android.os.Environment;

import com.xuemooc.edxapp.utils.util.IOUtils;
import com.xuemooc.edxapp.utils.util.HashCodeFileNameGenerator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by chaossss on 2015/9/24.
 */
public class BaseDiskCache implements DiskCache {
    public static final int DEFAULT_COMPRESS_QUALITY = 100;
    public static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;

    private static final String TEMP_IMAGE_POSTFIX = ".tmp";
    private static final String IMG_CACHE_DIR = Environment.getExternalStorageDirectory().getPath() + "/UESTC_MOOC/Image";

    protected final File cacheDir;

    public BaseDiskCache() {
        this.cacheDir = new File(IMG_CACHE_DIR);
        if(!cacheDir.exists()){
            cacheDir.mkdirs();
        }
    }

    @Override
    public File getDirectory() {
        return cacheDir;
    }

    @Override
    public boolean save(String imageUri, Bitmap image) throws IOException {
        File imageFile = getFile(imageUri);
        File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), DEFAULT_BUFFER_SIZE);
        boolean savedSuccessfully = false;
        try {
            savedSuccessfully = image.compress(DEFAULT_COMPRESS_FORMAT, DEFAULT_COMPRESS_QUALITY, os);
        } finally {
            IOUtils.closeSliently(os);
            if (savedSuccessfully && !tmpFile.renameTo(imageFile)) {
                savedSuccessfully = false;
            }
            if (!savedSuccessfully) {
                tmpFile.delete();
            }
        }
        return savedSuccessfully;
    }

    @Override
    public boolean save(String imageUri, InputStream imageStream, IOUtils.CopyListener listener) throws IOException {
        File imageFile = getFile(imageUri);

        boolean loaded = false;
        try{
            OutputStream os = new BufferedOutputStream(new FileOutputStream(imageFile), DEFAULT_BUFFER_SIZE);
            try{
                loaded = IOUtils.copyStream(imageStream, os, listener, DEFAULT_BUFFER_SIZE);
            } finally {
                IOUtils.closeSliently(os);
            }
        } finally {
            if(!loaded){
                imageFile.delete();
            }
        }

        return loaded;
    }

    @Override
    public boolean remove(String imageUri) {
        return getFile(imageUri).delete();
    }

    @Override
    public void close() {
    }

    @Override
    public void clear() {
        File[] files = cacheDir.listFiles();
        if(files != null){
            for(File f : files){
                f.delete();
            }
        }
    }

    public File getFile(String imageUri){
        return new File(cacheDir, HashCodeFileNameGenerator.generate(imageUri));
    }

    public String getFilePath(String imageUri){
        return getFile(imageUri).getAbsolutePath() + TEMP_IMAGE_POSTFIX;
    }
}
