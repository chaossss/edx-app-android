package com.xuemooc.edxapp.image.cache.memory;

import android.graphics.Bitmap;

public interface MemoryCache{
    /**
     * Caches image into memeory by key
     * @return true - if image was cached successfully; false - if image wasn't cache
     */
    boolean put(String key, Bitmap image);

    /**
     * Returns image by key
     * @return null if there is no image for key
     */
    Bitmap get(String key);

    /**
     * Removes image by key
     */
    Bitmap remove(String key);

    /**
     * Removes all image from cache
     */
    void clear();
}