package com.xuemooc.edxapp;

import android.test.InstrumentationTestCase;

import com.xuemooc.edxapp.http.Api;
import com.xuemooc.edxapp.model.api.AuthResponse;

/**
 * Created by hackeris on 15/7/28.
 */
public class ApiTest extends InstrumentationTestCase {

    public void testApi() {

        int a = 1, b = 1;
        assertEquals(a, b);
    }

    public void testAuth() throws Exception {

        Api api = new Api(null);
        AuthResponse authResponse = api.auth("Hackeris", "hackeris");
        assertEquals(authResponse.token_type, "Bearer");
    }
}
