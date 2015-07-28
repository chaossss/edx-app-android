package com.xuemooc.edxapp;

import android.app.Application;
import android.os.Bundle;
import android.test.ApplicationTestCase;

import com.xuemooc.edxapp.http.Api;
import com.xuemooc.edxapp.model.api.AuthResponse;

/**
 * Created by hackeris on 15/7/28.
 */
public class ApiTest extends ApplicationTestCase<Application> {

    public ApiTest() {
        super(Application.class);
    }

    public void testApi() {

        int a = 1, b = 1;
        assertEquals(a, b);
    }

    public void testSetCookieHeaders() throws Exception {

        Api api = new Api(getContext());
        Bundle bundle = new Bundle();
        bundle = api.setCookieHeaders(bundle);
        assertTrue(bundle.containsKey("Cookie"));
    }

    public void testAuth() throws Exception {

        Api api = new Api(getContext());
        AuthResponse authResponse = api.auth("Hackeris", "hackeris");
        assertEquals(authResponse.token_type, "Bearer");
    }
}
