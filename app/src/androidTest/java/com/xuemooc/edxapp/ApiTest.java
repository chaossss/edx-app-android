package com.xuemooc.edxapp;

import android.app.Application;
import android.os.Bundle;
import android.test.ApplicationTestCase;

import com.xuemooc.edxapp.http.Api;
import com.xuemooc.edxapp.model.api.AuthResponse;
import com.xuemooc.edxapp.model.api.EnrolledCoursesResponse;
import com.xuemooc.edxapp.model.api.ProfileModel;
import com.xuemooc.edxapp.model.api.ResetPasswordResponse;
import com.xuemooc.edxapp.module.prefs.PrefManager;

import java.util.ArrayList;

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

    @Deprecated
    public void testGetProfileWithName() throws Exception {

        ProfileModel profile = api.getProfile("Hackeris");
        assertEquals(profile.username, "Hackeris");
    }

    public void testGetProfile() throws Exception {

        ProfileModel profile = api.getProfile();
        assertEquals(profile.username, "Hackeris");
    }

    public void testGetCurrentUserProfile() throws Exception {

        ProfileModel profile = new PrefManager(getContext(), PrefManager.Pref.LOGIN).getCurrentUserProfile();
        assertEquals(profile.username, "Hackeris");
    }

    public void testGetEnrolledCourse() throws Exception {

        ArrayList<EnrolledCoursesResponse> courses = api.getEnrolledCourses(false);
        assertTrue(courses.size() > 0);
    }

    public void testGetEnrolledCourseFromCache() throws Exception {

        api.getProfile();
        ArrayList<EnrolledCoursesResponse> courses = api.getEnrolledCourses(true);
        assertTrue(courses.size() > 0);
    }

    public void testResetPassword() throws Exception {

        api.getProfile();
        ResetPasswordResponse resp = api.resetPassword("hackeris@qq.com");
        assertTrue(resp.isSuccess());
    }
}
