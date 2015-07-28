package com.xuemooc.edxapp;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.xuemooc.edxapp.http.HttpManager;

import java.io.IOException;

/**
 * Created by hackeris on 15/7/28.
 */
public class HttpManagerTest extends InstrumentationTestCase {

    public void testDemo() {

        int a = 1, b = 1;
        assertEquals(a, b);
    }

    public void testGet() throws IOException {

        HttpManager manager = new HttpManager();
        String result = manager.get("http://blog.api.android.com/test?key=k&value=v", null);
        assertEquals(result, "GET:k=v");
    }

    public void testPost() throws IOException{

        HttpManager manager = new HttpManager();
        String result = manager.post("http://blog.api.android.com/test", "key=k&value=v", null);
        assertEquals(result, "POST:k=v");
    }

    public void testPostWithBundle() throws IOException {

        Bundle bundle = new Bundle();
        bundle.putString("key", "k");
        bundle.putString("value", "v");
        HttpManager manager = new HttpManager();
        String result = manager.post("http://blog.api.android.com/test", bundle, null);
        assertEquals(result, "POST:k=v");
    }
}
