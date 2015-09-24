package com.xuemooc.edxapp.image.cache.disk;

import android.graphics.Bitmap;

import com.xuemooc.edxapp.image.cache.disk.naming.FileNameGenerator;
import com.xuemooc.edxapp.image.cache.disk.naming.HashCodeFileNameGenerator;
import com.xuemooc.edxapp.image.utils.IOUtils;

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
    public static final int DEFAULT_BUFFER_SIZE = 32 * 10274;
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;

    private static final String TEMP_IMAGE_POSTFIX = ".tmp";
    private static final String ERROR_ARG_NULL = "argument must be not null";

    protected final File cacheDir;
    protected final File reserveCacheDir;
    protected final FileNameGenerator fileNameGenerator;

    protected int bufferSize = DEFAULT_BUFFER_SIZE;
    protected int compressQuality = DEFAULT_COMPRESS_QUALITY;
    protected Bitmap.CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;

    public BaseDiskCache(File cacheDir) {
        this(cacheDir, null);
    }

    public BaseDiskCache(File cacheDir, File reserveCacheDir) {
        if(cacheDir == null){
            throw new IllegalArgumentException("cacheDir" + ERROR_ARG_NULL);
        }

        this.cacheDir = cacheDir;
        this.reserveCacheDir = reserveCacheDir;
        this.fileNameGenerator = new HashCodeFileNameGenerator();
    }

    @Override
    public File getDirectory() {
        return cacheDir;
    }

    @Override
    public File get(String imageUri) {
        return getFile(imageUri);
    }

    @Override
    public boolean save(String imageUri, Bitmap image) throws IOException {
        File imageFile = getFile(imageUri);
        File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), bufferSize);

        boolean savedSuccessfully = false;
        try{
            savedSuccessfully = image.compress(compressFormat, compressQuality, os);
        } finally {
            IOUtils.closeSliently(os);
            if(savedSuccessfully && !tmpFile.renameTo(imageFile)){
                savedSuccessfully = false;
            }

            if(!savedSuccessfully){
                tmpFile.delete();
            }
        }

        image.recycle();
        return savedSuccessfully;
    }

    @Override
    public boolean save(String imageUri, InputStream imageStream, IOUtils.CopyListener listener) throws IOException {
        File imageFile = get(imageUri);
        File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);

        boolean loaded = false;
        try{
            OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), bufferSize);
            try{
                loaded = IOUtils.copyStream(imageStream, os, listener, bufferSize);
            } finally {
                IOUtils.closeSliently(os);
            }
        } finally {
            if(loaded && !tmpFile.renameTo(imageFile)){
                loaded = false;
            }

            if(!loaded){
                tmpFile.delete();
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

    protected File getFile(String imageUri){
        String fileName = fileNameGenerator.generate(imageUri);
        File dir = cacheDir;

        if(!cacheDir.exists() && !cacheDir.mkdirs()){
            if(reserveCacheDir != null && (reserveCacheDir.exists() || reserveCacheDir.mkdirs())){
                dir = reserveCacheDir;
            }
        }

        return new File(dir, fileName);
    }
}
