package com.xuemooc.edxapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.xuemooc.edxapp.http.util.CacheManager;

import java.io.IOException;

/**
 * Created by hackeris on 15/7/28.
 */
public class CacheManagerTest extends ApplicationTestCase<Application> {

    private CacheManager mCacheManager;

    public CacheManagerTest(){

        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCacheManager = new CacheManager(getContext());
        mCacheManager.clean();
    }

    public void testPut() throws IOException {

        String testUrl1 = "http://www.baidu.com";
        String testContent1 = "This is from baidu";

        String testUrl2 = "https://www.google.com";
        String testContent2 = "This is from google";

        mCacheManager.put(testUrl1, testContent1);
        mCacheManager.put(testUrl2, testContent2);
        String cache = mCacheManager.get(testUrl1);
        assertEquals(testContent1, cache);
        cache = mCacheManager.get(testUrl2);
        assertEquals(testContent2, cache);
    }

    public void testHas() throws IOException {
        String testUrl1 = "http://www.baidu.com";
        String testContent1 = "This is from baidu";

        mCacheManager.put(testUrl1,testContent1);

        assertFalse(mCacheManager.has("https://www.google.com"));
        assertTrue(mCacheManager.has("http://www.baidu.com"));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
