package com.xuemooc.edxapp.image.cache.disk;

import android.graphics.Bitmap;

import com.xuemooc.edxapp.image.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaossss on 2015/9/24.
 */
public class LimitedAgeDiskCache extends BaseDiskCache {
    private final long maxFileAge;
    private final Map<File, Long> loadingDates = Collections.synchronizedMap(new HashMap<File, Long>());

    public LimitedAgeDiskCache(File cacheDir, long maxFileAge) {
        this(cacheDir, null, maxFileAge);
    }

    public LimitedAgeDiskCache(File cacheDir, File reserveCacheDir, long maxFileAge) {
        super(cacheDir, reserveCacheDir);
        this.maxFileAge = maxFileAge * 1000;
    }

    @Override
    public File get(String imageUri) {
        File file =  super.get(imageUri);
        if(file != null && file.exists()){
            boolean cached;
            Long loadingDate = loadingDates.get(file);

            if(loadingDate == null){
                cached = false;
                loadingDate = file.lastModified();
            } else {
                cached = true;
            }

            if(System.currentTimeMillis() - loadingDate > maxFileAge){
                file.delete();
                loadingDates.remove(file);
            } else if(!cached){
                loadingDates.put(file, loadingDate);
            }
        }

        return file;
    }

    @Override
    public boolean save(String imageUri, Bitmap image) throws IOException {
        boolean saved = super.save(imageUri, image);
        rememberUsage(imageUri);
        return saved;
    }

    @Override
    public void clear() {
        super.clear();
        loadingDates.clear();
    }

    @Override
    public boolean remove(String imageUri) {
        loadingDates.remove(getFile(imageUri));
        return super.remove(imageUri);
    }

    @Override
    public boolean save(String imageUri, InputStream imageStream, IOUtils.CopyListener listener) throws IOException {
        boolean saved = super.save(imageUri, imageStream, listener);
        rememberUsage(imageUri);
        return saved;
    }

    private void rememberUsage(String imageUri){
        File file = getFile(imageUri);
        long currentTime = System.currentTimeMillis();
        file.setLastModified(currentTime);
        loadingDates.put(file, currentTime);
    }
}
