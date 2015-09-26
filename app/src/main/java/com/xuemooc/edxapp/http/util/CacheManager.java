package com.xuemooc.edxapp.http.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Created by hackeris on 15/7/28.
 */
public class CacheManager {

    private File mCacheFolder;

    public CacheManager(Context context) {

        mCacheFolder = new File(context.getFilesDir(), "http-cache");
        if (!mCacheFolder.exists()) {
            mCacheFolder.mkdirs();
        }
    }

    public boolean has(String url) {
        String hash = stringToMD5(url);
        File file = new File(mCacheFolder, hash);
        return file.exists();
    }

    public void put(String url, String content) throws IOException {

        String hash = stringToMD5(url);
        File file = new File(mCacheFolder, hash);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes());
        fos.close();
    }

    public String get(String url) throws IOException {

        String hash = stringToMD5(url);
        File file = new File(mCacheFolder, hash);

        if (!file.exists()) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();

        return new String(buffer, Charset.defaultCharset());
    }

    private String stringToMD5(String str) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int v : md5Bytes) {
            int val = (v) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public void clean() {

        File[] cacheFiles = mCacheFolder.listFiles();
        for(File cache: cacheFiles){
            cache.delete();
        }
    }
}
