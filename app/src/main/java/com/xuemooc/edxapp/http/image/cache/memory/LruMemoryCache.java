package com.xuemooc.edxapp.http.image.cache.memory;

import android.graphics.Bitmap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A cache that holds strong reference to a limited number of Bitmaps.Each time a Bitmap is accessed, it is moved to the head of a queue.
 * When a Bitmap is added to a full cache, the Bitmap at the end of that queue is evicted
 * Created by chaossss on 2015/9/24.
 */
public class LruMemoryCache implements MemoryCache{
    private int size;
    private final int maxCacheSize = 4 * 1024 * 1024;
    private final LinkedHashMap<String, Bitmap> lruCacheMap;

    public LruMemoryCache() {
        lruCacheMap = new LinkedHashMap<>(0, 0.75f, true);
    }

    @Override
    public final boolean put(String key, Bitmap image) {
        if(key == null || image == null){
            throw new NullPointerException("key == null or image == null");
        }

        synchronized (this){
            size += sizeOf(image);
            Bitmap previous = lruCacheMap.put(key, image);

            if(previous != null){
                size -= sizeOf(previous);
            }
        }

        trimToSize(maxCacheSize);
        return true;
    }

    @Override
    public final Bitmap get(String key) {
        if(key == null){
            throw new NullPointerException("key == null");
        }

        synchronized (this){
            return lruCacheMap.get(key);
        }
    }

    @Override
    public final Bitmap remove(String key) {
        if(key == null){
            throw new NullPointerException("key == null");
        }

        synchronized (this){
            Bitmap previous = lruCacheMap.remove(key);
            if(previous != null){
                size -= sizeOf(previous);
            }
            return previous;
        }
    }

    @Override
    public void clear() {
        trimToSize(-1);
    }

    private int sizeOf(Bitmap image){
        return image.getRowBytes() * image.getHeight();
    }

    private void trimToSize(int maxCacheSize){
        while(true){
            String key;
            Bitmap image;

            synchronized (this){
                if(size < 0 || (lruCacheMap.isEmpty()) && size != 0){
                    throw new IllegalStateException(getClass().getName() + "sizeOf() is reporting inconsistent results");
                }

                if(size <= maxCacheSize || lruCacheMap.isEmpty()){
                    break;
                }

                Map.Entry<String, Bitmap> toEvict = lruCacheMap.entrySet().iterator().next();
                if(toEvict == null){
                    break;
                }

                key = toEvict.getKey();
                image = toEvict.getValue();
                lruCacheMap.remove(key);
                size -= sizeOf(image);
            }
        }
    }
}
