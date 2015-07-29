package com.xuemooc.edxapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.xuemooc.edxapp.http.CacheManager;

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

        String mTestUrl = "http://www.baidu.com";
        String mTestContent = "This is from baidu";

        mCacheManager.put(mTestUrl, mTestContent);
        String cache = mCacheManager.get(mTestUrl);
        assertEquals(mTestContent, cache);
    }

    public void testHas() {

        assertFalse(mCacheManager.has("https://www.google.com"));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
