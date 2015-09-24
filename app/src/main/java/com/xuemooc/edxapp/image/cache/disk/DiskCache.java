package com.xuemooc.edxapp.image.cache.disk;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for disk cache
 * Created by chaossss on 2015/9/24.
 */
public interface DiskCache {
    /**
     * Returns root directory of disk cache
     * @return Root directory of disk cache
     */
    File getDirectory();

    /**
     * Retruns file of cached image
     * @return return File of cached image or null if image wasn't cached
     */
    File get(String imageUri);

    /**
     * Saves image stream in disk cache.
     * @return true - if image was saved successfully; false - if image wasn't saved in disk cache
     * @throws java.io.IOException
     */
    boolean save(String imageUri, Bitmap image) throws IOException;

    /**
     * Saves image stream in disk cache.
     * @param imageStream Input stream of image(shouldn't be closed in this method)
     * @return true - if image was saved successfully; false - if image wasn't saved in disk cache
     * @throws java.io.IOException
     */
    boolean save(String imageUri, InputStream imageStream) throws IOException;

    /**
     * Removes image file associated with incoming Uri
     * @return true - if image file is deleted successfully;
     * false - if image file doesn't exist for incoming Uri or image file can't be deleted
     */
    boolean remove(String imageUri);

    /**
     * Closes disk cache
     */
    void close();

    /**
     * Clears disk cache
     */
    void clear();
}
