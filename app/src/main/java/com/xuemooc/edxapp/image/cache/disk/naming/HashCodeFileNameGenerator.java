package com.xuemooc.edxapp.image.cache.disk.naming;

/**
 * Created by chaossss on 2015/9/24.
 */
public class HashCodeFileNameGenerator implements FileNameGenerator {
    @Override
    public String generate(String imageUri) {
        return String.valueOf(imageUri.hashCode());
    }
}
