package com.xuemooc.edxapp.http.image.utils;

/**
 * Created by chaossss on 2015/9/24.
 */
public class HashCodeFileNameGenerator{

    private HashCodeFileNameGenerator() {
    }

    public static String generate(String imageUri) {
        return String.valueOf(imageUri.hashCode());
    }
}
