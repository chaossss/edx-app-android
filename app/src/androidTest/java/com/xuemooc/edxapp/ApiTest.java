package com.xuemooc.edxapp;

import android.app.Application;
import android.os.Bundle;
import android.test.ApplicationTestCase;

import com.xuemooc.edxapp.http.Api;
import com.xuemooc.edxapp.model.api.AuthResponse;
import com.xuemooc.edxapp.model.api.ProfileModel;

/**
 * Created by hackeris on 15/7/28.
 */
public class ApiTest extends ApplicationTestCase<Application> {

    private Api api;

    public ApiTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        api = new Api(getContext());
    }

    public void testApi() {

        int a = 1, b = 1;
        assertEquals(a, b);
    }

    public void testSetCookieHeaders() throws Exception {

        Bundle bundle = new Bundle();
        bundle = api.setCookieHeaders(bundle);
        assertTrue(bundle.containsKey("Cookie"));
    }

    public void testAuth() throws Exception {

        AuthResponse authResponse = api.auth("Hackeris", "hackeris");
        assertEquals(authResponse.token_type, "Bearer");
    }

    public void testGetProfile() throws Exception {

        ProfileModel profile = api.getProfile("Hackeris");
        assertEquals(profile.username, "Hackeris");
    }
}
