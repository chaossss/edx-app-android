package com.xuemooc.edxapp;

import android.app.Application;
import android.os.Bundle;
import android.test.ApplicationTestCase;

import com.xuemooc.edxapp.http.Api;
import com.xuemooc.edxapp.http.IApi;
import com.xuemooc.edxapp.model.api.AuthResponse;
import com.xuemooc.edxapp.model.api.EnrolledCoursesResponse;
import com.xuemooc.edxapp.model.api.ProfileModel;
import com.xuemooc.edxapp.model.api.ResetPasswordResponse;
import com.xuemooc.edxapp.model.api.SectionEntry;
import com.xuemooc.edxapp.model.api.VideoResponseModel;
import com.xuemooc.edxapp.module.prefs.PrefManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by hackeris on 15/7/28.
 */
public class ApiTest extends ApplicationTestCase<Application> {
    private IApi api;

    private String testUserEmail = "hackeris@qq.com";
    private String testUserName = "Hackeris";
    private String testUserPassword = "hackeris";
//    private String testCourseId = "edX/DemoX/Demo_Course";
    private String testCourseId = "UESTC/SLL001/2014-2015-1";

    public ApiTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        api = new Api(getContext());
        api.auth(testUserName, testUserPassword);
    }

    public void testSetCookieHeaders() throws Exception {

        Bundle bundle = new Bundle();
        bundle = api.setCookieHeaders(bundle);
        assertTrue(bundle.containsKey("Cookie"));
    }

    public void testAuth() throws Exception {

        AuthResponse authResponse = api.auth(testUserName, testUserPassword);
        assertEquals(authResponse.token_type, "Bearer");
    }

    @Deprecated
    public void testGetProfileWithName() throws Exception {

        ProfileModel profile = api.getProfile(testUserName);
        assertEquals(profile.username, testUserName);
    }

    public void testGetProfile() throws Exception {

        ProfileModel profile = api.getProfile();
        assertEquals(profile.username, testUserName);
    }

    public void testGetCurrentUserProfile() throws Exception {

        ProfileModel profile = new PrefManager(getContext(), PrefManager.Pref.LOGIN).getCurrentUserProfile();
        assertEquals(profile.username, testUserName);
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
        ResetPasswordResponse resp = api.resetPassword(testUserEmail);
        assertTrue(resp.isSuccess());
    }

    public void testGetCourseHierarchy() throws Exception {

        Map<String, SectionEntry> courseHierarchy = api.getCourseHierarchy(testCourseId);
        assertFalse(courseHierarchy.isEmpty());
    }

    public void testGetVideosByCourseId() throws Exception {

        ArrayList<VideoResponseModel> videos = api.getVideosByCourseId(testCourseId, false);
        assertFalse(videos.isEmpty());
    }
}
