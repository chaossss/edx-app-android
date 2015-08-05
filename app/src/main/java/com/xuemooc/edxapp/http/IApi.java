package com.xuemooc.edxapp.http;

import android.os.Bundle;

import com.xuemooc.edxapp.model.api.AnnouncementsModel;
import com.xuemooc.edxapp.model.api.AuthResponse;
import com.xuemooc.edxapp.model.api.CourseEntry;
import com.xuemooc.edxapp.model.api.CourseInfoModel;
import com.xuemooc.edxapp.model.api.EnrolledCoursesResponse;
import com.xuemooc.edxapp.model.api.HandoutModel;
import com.xuemooc.edxapp.model.api.LectureModel;
import com.xuemooc.edxapp.model.api.ProfileModel;
import com.xuemooc.edxapp.model.api.RegisterResponse;
import com.xuemooc.edxapp.model.api.ResetPasswordResponse;
import com.xuemooc.edxapp.model.api.SectionEntry;
import com.xuemooc.edxapp.model.api.SectionItemInterface;
import com.xuemooc.edxapp.model.api.SyncLastAccessedSubsectionResponse;
import com.xuemooc.edxapp.model.api.TranscriptModel;
import com.xuemooc.edxapp.model.api.VideoResponseModel;
import com.xuemooc.edxapp.module.registration.RegistrationDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hackeris on 15/8/5.
 */
public interface IApi {

    String getBaseUrl();

    Bundle setCookieHeaders(Bundle headerBundle) throws Exception;

    AuthResponse auth(String username, String password)
            throws Exception;

    ProfileModel getProfile(String username) throws Exception;

    Bundle getAuthHeaders();

    ProfileModel getProfile() throws Exception;

    ResetPasswordResponse resetPassword(String emailId)
            throws Exception;

    ArrayList<EnrolledCoursesResponse> getEnrolledCourses(boolean fetchFromCache) throws Exception;

    Map<String, SectionEntry> getCourseHierarchy(String courseId)
            throws Exception;

    Map<String, SectionEntry> getCourseHierarchy(String courseId, boolean preferCache)
                    throws Exception;

    LectureModel getLecture(String courseId, String chapterName, String lectureName)
                            throws Exception;

    VideoResponseModel getVideoById(String courseId, String videoId)
                                    throws Exception;

    VideoResponseModel getSubsectionById(String courseId, String subsectionId)
                                            throws Exception;

    String getUnitUrlByVideoById(String courseId, String videoId)
                                                    throws Exception;

    ArrayList<EnrolledCoursesResponse> getEnrolledCourses()
                                                            throws Exception;

    CourseEntry getCourseById(String courseId);

    ArrayList<VideoResponseModel> getVideosByCourseId(String courseId, boolean preferCache)
            throws Exception;

    HandoutModel getHandout(String url, boolean preferCache) throws Exception;

    CourseInfoModel getCourseInfo(String url, boolean preferCache) throws Exception;

    List<AnnouncementsModel> getAnnouncement(String url, boolean preferCache)
            throws Exception;

    CourseInfoModel srtStream(String url, boolean preferCache) throws Exception;

    TranscriptModel getTranscriptsOfVideo(String enrollmentId,
                                          String videoId) throws Exception;

    String downloadTranscript(String url)
                                                         throws Exception;

    ArrayList<VideoResponseModel> getVideosByURL(String courseId, String videoUrl, boolean preferCache)
                                                                 throws Exception;

    ArrayList<SectionItemInterface> getLiveOrganizedVideosByChapter
            (String courseId, String chapter);

    SyncLastAccessedSubsectionResponse syncLastAccessedSubsection(String courseId,
                                                                  String lastVisitedModuleId) throws Exception;

    SyncLastAccessedSubsectionResponse getLastAccessedSubsection(String courseId) throws Exception;

    RegisterResponse register(Bundle parameters)
                                                                                                                                              throws Exception;

    RegistrationDescription getRegistrationDescription() throws Exception;

    Boolean enrollInACourse(String courseId, boolean email_opt_in) throws Exception;
}
