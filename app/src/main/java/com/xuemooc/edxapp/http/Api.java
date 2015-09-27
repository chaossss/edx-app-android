package com.xuemooc.edxapp.http;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xuemooc.edxapp.http.util.CacheManager;
import com.xuemooc.edxapp.http.util.HttpManager;
import com.xuemooc.edxapp.model.api.AnnouncementsModel;
import com.xuemooc.edxapp.model.api.ChapterModel;
import com.xuemooc.edxapp.model.api.CourseEntry;
import com.xuemooc.edxapp.model.api.CourseInfoModel;
import com.xuemooc.edxapp.model.api.FormFieldMessageBody;
import com.xuemooc.edxapp.model.api.HandoutModel;
import com.xuemooc.edxapp.model.api.LectureModel;
import com.xuemooc.edxapp.model.api.RegisterResponse;
import com.xuemooc.edxapp.module.registration.RegistrationDescription;
import com.xuemooc.edxapp.model.api.SectionEntry;
import com.xuemooc.edxapp.model.api.SectionItemInterface;
import com.xuemooc.edxapp.model.api.SectionItemModel;
import com.xuemooc.edxapp.model.api.SyncLastAccessedSubsectionResponse;
import com.xuemooc.edxapp.model.api.TranscriptModel;
import com.xuemooc.edxapp.model.api.VideoResponseModel;
import com.xuemooc.edxapp.utils.util.Config;
import com.xuemooc.edxapp.utils.exception.AuthException;
import com.xuemooc.edxapp.model.api.AuthErrorResponse;
import com.xuemooc.edxapp.model.api.AuthResponse;
import com.xuemooc.edxapp.model.api.EnrolledCoursesResponse;
import com.xuemooc.edxapp.model.api.ProfileModel;
import com.xuemooc.edxapp.model.api.ResetPasswordResponse;
import com.xuemooc.edxapp.module.prefs.PrefManager;
import com.xuemooc.edxapp.utils.util.DateUtil;
import com.xuemooc.edxapp.utils.util.NetworkUtil;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hackeris on 15/7/28.
 */
public class Api implements IApi {

    private Context mContext;

    private HttpManager http;

    private CacheManager mCache;

    @Override
    public String getBaseUrl() {
        return Config.getInstance().getBaseUrl();
    }

    public Api(Context context) {
        this.mContext = context;
        this.http = new HttpManager();
        this.mCache = new CacheManager(context);
    }

    /**
     * Returns list of headers for a particular Get request.
     *
     * @return
     * @throws Exception
     */
    private Map<String, List<String>> getLoginResponseHeaders()
            throws Exception {
        String url = getBaseUrl() + "/login";
        return http.getRequestHeader(url);
    }

    private String getCsrfToken(Map<String, List<String>> map){

        String cookie = map.get("Set-Cookie").get(0);
        String[] values = cookie.split(";");
        String csrfPair = null;
        for(String value: values){
            if(value.contains("csrftoken")){
                csrfPair = value;
            }
        }
        if(csrfPair == null){
            return "";
        }
        return csrfPair.substring("csrftoken".length() + 1);
    }

    /**
     * Sets cookie headers like "X-CSRFToken" in the given bundle.
     * This method is helpful in making API calls the way website does.
     *
     * @param headerBundle
     * @return
     * @throws Exception
     */
    @Override
    public Bundle setCookieHeaders(Bundle headerBundle) throws Exception {
        Map<String, List<String>> headers = getLoginResponseHeaders();
        String csrfToken = getCsrfToken(headers);

        headerBundle.putString("Cookie", "csrftoken" + "=" + csrfToken);
        headerBundle.putString("X-CSRFToken", csrfToken);
        return headerBundle;
    }

    /**
     * Executes HTTP POST for auth call, and returns response.
     *
     * @return
     * @throws Exception
     */
    @Override
    public AuthResponse auth(String username, String password)
            throws Exception {
        Bundle p = new Bundle();
        p.putString("grant_type", "password");
        p.putString("client_id", Config.getInstance().getClientId());
        p.putString("client_secret", Config.getInstance().getClientSecret());
        p.putString("username", username);
        p.putString("password", password);

        String url = getBaseUrl() + "/oauth2/access_token/";
        String json = http.post(url, p, null);
        //logger.debug("Auth response= " + json);

        // store auth token response
        PrefManager pref = new PrefManager(mContext, PrefManager.Pref.LOGIN);
        pref.put(PrefManager.Key.AUTH_JSON, json);

        Gson gson = new GsonBuilder().create();
        AuthResponse res = gson.fromJson(json, AuthResponse.class);

        return res;
    }

    /**
     * Returns basic profile information of the given username.
     * @deprecated Use {@link #getProfile()} instead.
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public ProfileModel getProfile(String username) throws Exception {
        Bundle p = new Bundle();
        p.putString("username", username);

        String url = getBaseUrl() + "/api/mobile/v0.5/users/" + username;
        String json = http.get(url, getAuthHeaders());

        Gson gson = new GsonBuilder().create();
        ProfileModel res = gson.fromJson(json, ProfileModel.class);
        // hold the json string as it is
        res.json = json;

        //logger.debug("profile=" + json);

        return res;
    }

    /**
     * Returns "Authorization" header with current active access token.
     * @return
     */
    @Override
    public Bundle getAuthHeaders() {
        Bundle headers = new Bundle();

        // generate auth headers
        PrefManager pref = new PrefManager(mContext, PrefManager.Pref.LOGIN);
        AuthResponse auth = pref.getCurrentAuth();

        if (auth == null || !auth.isSuccess()) {
            return null;
        } else {
            headers.putString("Authorization", String.format("%s %s", auth.token_type, auth.access_token));
        }
        return headers;
    }

    /**
     * Returns user's basic profile information for current active session.
     * @return
     * @throws Exception
     */
    @Override
    public ProfileModel getProfile() throws Exception {
        Bundle p = new Bundle();
        p.putString("format", "json");

        String url = getBaseUrl() + "/api/mobile/v0.5/my_user_info";
        String urlWithAppendedParams = HttpManager.toGetUrl(url, p);

        //logger.debug("Url for getProfile: " + urlWithAppendedParams);

        String json = http.get(urlWithAppendedParams, getAuthHeaders());

        if (json == null) {
            return null;
        }
        //logger.debug("GetProfile response=" + json);

        Gson gson = new GsonBuilder().create();
        ProfileModel res = gson.fromJson(json, ProfileModel.class);

        // store profile json
        if (res == null) {
            return null;
        }
        // hold the json string as it is
        res.json = json;
        PrefManager pref = new PrefManager(mContext, PrefManager.Pref.LOGIN);
        pref.put(PrefManager.Key.PROFILE_JSON, res.json);

        return res;
    }

    /**
     * Resets password for the given email id.
     * @param emailId
     * @return
     * @throws Exception
     */
    @Override
    public ResetPasswordResponse resetPassword(String emailId)
            throws Exception {
        Bundle headerBundle = new Bundle();
        headerBundle = setCookieHeaders(headerBundle);

        Bundle params = new Bundle();
        params.putString("email", emailId);

        String url = getBaseUrl() + "/password_reset/";

        String json = http.post(url, params, headerBundle);

        if (json == null) {
            return null;
        }
        //logger.debug("Reset password response=" + json);

        // store auth token response
        PrefManager pref = new PrefManager(mContext, PrefManager.Pref.LOGIN);
        pref.put(PrefManager.Key.AUTH_JSON, json);

        Gson gson = new GsonBuilder().create();
        ResetPasswordResponse res = gson.fromJson(json, ResetPasswordResponse.class);

        return res;
    }

    /**
     * Returns enrolled courses of given user.
     *
     * @param fetchFromCache
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<EnrolledCoursesResponse> getEnrolledCourses(boolean fetchFromCache) throws Exception {
        PrefManager pref = new PrefManager(mContext, PrefManager.Pref.LOGIN);

        Bundle p = new Bundle();
        p.putString("format", "json");
        String url = getBaseUrl() + "/api/mobile/v0.5/users/" + pref.getCurrentUserProfile().username
                + "/course_enrollments/";
        String json = null;

        if (NetworkUtil.isConnected(mContext) && !fetchFromCache) {
            // get data from server
            String urlWithAppendedParams = HttpManager.toGetUrl(url, p);
            json = http.get(urlWithAppendedParams, getAuthHeaders());
            // cache the response
            mCache.put(url, json);
        }

        if(json == null) {
            json = mCache.get(url);
        }

        if (json == null) {
            return null;
        }

        //logger.debug("Url "+"enrolled_courses=" + json);

        Gson gson = new GsonBuilder().create();

        AuthErrorResponse authError = null;
        try {
            // check if auth error
            authError = gson.fromJson(json, AuthErrorResponse.class);
        } catch(Exception ex) {
            // nothing to do here
        }
        if (authError != null && authError.detail != null) {
            throw new AuthException(authError);
        }

        TypeToken<ArrayList<EnrolledCoursesResponse>> t = new TypeToken<ArrayList<EnrolledCoursesResponse>>() {
        };

        ArrayList<EnrolledCoursesResponse> list = gson.fromJson(json,
                t.getType());

        return list;
    }

    /**
     * Returns entire course hierarchy.
     *
     * @param courseId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, SectionEntry> getCourseHierarchy(String courseId)
            throws Exception {
        return getCourseHierarchy(courseId, false);
    }

    /**
     * Returns entire course hierarchy.
     *
     * @param courseId
     * @param preferCache
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, SectionEntry> getCourseHierarchy(String courseId, boolean preferCache)
            throws Exception {
        Bundle p = new Bundle();
        p.putString("format", "json");
        String url = getBaseUrl() + "/api/mobile/v0.5/video_outlines/courses/" + courseId;
        //logger.debug("Get course heirarchy url - "+url);
        String json = null;
        if (NetworkUtil.isConnected(mContext) && !preferCache) {
            // get data from server
            String urlWithAppendedParams = HttpManager.toGetUrl(url, p);
            json = http.get(urlWithAppendedParams, getAuthHeaders());
            // cache the response
            mCache.put(url, json);
        } else {
            json = mCache.get(url);
        }

        if (json == null) {
            return null;
        }

        //Initializing task call
        //logger.debug("Received Data from Server at : "+ DateUtil.getCurrentTimeStamp());
        //logger.debug("course_hierarchy= " + json);

        Gson gson = new GsonBuilder().create();
        TypeToken<ArrayList<VideoResponseModel>> t = new TypeToken<ArrayList<VideoResponseModel>>() {
        };

        ArrayList<VideoResponseModel> list = gson.fromJson(json, t.getType());

        // create hierarchy with chapters, sections and subsections
        // HashMap<String, SectionEntry> chapterMap = new HashMap<String, SectionEntry>();
        Map<String, SectionEntry> chapterMap = new LinkedHashMap<String, SectionEntry>();
        for (VideoResponseModel m : list) {
            // add each video to its corresponding chapter and section

            // add this as a chapter
            String cname = m.getChapter().name;

            // carry this courseId with video model
            m.setCourseId(courseId);

            SectionEntry s = null;
            if (chapterMap.containsKey(cname)) {
                s = chapterMap.get(cname);
            } else {
                s = new SectionEntry();
                s.chapter = cname;
                s.isChapter = true;
                s.section_url = m.section_url;
                chapterMap.put(cname, s);
            }

            // add this video to section inside in this chapter
            ArrayList<VideoResponseModel> videos = s.sections.get(m.getSection().name);
            if (videos == null) {
                s.sections.put(m.getSection().name,
                        new ArrayList<VideoResponseModel>());
                videos = s.sections.get(m.getSection().name);
            }

            //This has been commented out because for some Videos thereare no english srt's and hence returning empty
            /*try{
            // FIXME: Utter hack code that should be removed as soon as the server starts
            // returning default english transcripts.
            if (m.getSummary().getTranscripts().englishUrl == null) {
                // Example URL: "http://mobile3.m.sandbox.edx.org/api/mobile/v0.5/video_outlines/transcripts/MITx/6.002x_4x/3T2014/Welcome/en";
                String usageKeyParts[] = m.getSummary().getId().split("/");
                String usageKey = usageKeyParts[usageKeyParts.length - 1];
                String fallbackUrl = getBaseUrl() + "/api/mobile/v0.5/video_outlines/transcripts/" + courseId + "/" + usageKey + "/en";
                m.getSummary().getTranscripts().englishUrl = fallbackUrl;
            }
            }catch(Exception e){
                logger.error(e);
            }*/
            videos.add(m);
        }

        //logger.debug("Finished converting data at "+ DateUtil.getCurrentTimeStamp());
        return chapterMap;
    }


    /**
     * Returns lecture model for given course id, chapter name and lecture name combination.
     * @param courseId
     * @param chapterName
     * @param lectureName
     * @return
     * @throws Exception
     */
    @Override
    public LectureModel getLecture(String courseId, String chapterName, String lectureName)
            throws Exception {
        Map<String, SectionEntry> map = getCourseHierarchy(courseId, true);

        for (Map.Entry<String, SectionEntry> chapterentry : map.entrySet()) {

            // identify required chapter
            if (chapterName.equals(chapterentry.getKey())) {
                for (Map.Entry<String, ArrayList<VideoResponseModel>> entry
                        : chapterentry.getValue().sections.entrySet()) {

                    // identify required lecture
                    if (entry.getKey().equals(lectureName)) {
                        LectureModel m = new LectureModel();
                        m.name = entry.getKey();
                        m.videos = entry.getValue();
                        return m;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns video model for given course id and video id.
     * @param courseId
     * @param videoId
     * @return
     * @throws Exception
     */
    @Override
    public VideoResponseModel getVideoById(String courseId, String videoId)
            throws Exception {
        Map<String, SectionEntry> map = getCourseHierarchy(courseId, true);

        // iterate chapters
        for (Map.Entry<String, SectionEntry> chapterentry : map.entrySet()) {
            // iterate lectures
            for (Map.Entry<String, ArrayList<VideoResponseModel>> entry :
                    chapterentry.getValue().sections.entrySet()) {
                // iterate videos
                for (VideoResponseModel v : entry.getValue()) {

                    // identify the video
                    if (videoId.equals(v.getSummary().getId())) {
                        return v;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Returns any (mostly first video always) one video {@link VideoResponseModel}
     * object from subsection identified by
     * given module id (subsectionId).
     * @param courseId
     * @param subsectionId
     * @return
     * @throws Exception
     */
    @Override
    public VideoResponseModel getSubsectionById(String courseId, String subsectionId)
            throws Exception {
        Map<String, SectionEntry> map = getCourseHierarchy(courseId, true);

        // iterate chapters
        for (Map.Entry<String, SectionEntry> chapterentry : map.entrySet()) {
            // iterate lectures
            for (Map.Entry<String, ArrayList<VideoResponseModel>> entry :
                    chapterentry.getValue().sections.entrySet()) {
                // iterate videos
                for (VideoResponseModel v : entry.getValue()) {
                    // identify the subsection (module) if id matches
                    if (subsectionId.equals(v.getSection().id)) {
                        return v;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Returns UnitUrl for given course id and video id.
     * @param courseId
     * @param videoId
     * @return
     * @throws Exception
     */
    @Override
    public String getUnitUrlByVideoById(String courseId, String videoId)
            throws Exception {
        try{
            VideoResponseModel vrm = getVideoById(courseId, videoId);
            if(vrm!=null){
                return vrm.unit_url;
            }
        }catch(Exception e){
            //logger.error(e);
            Log.e("Exception in api",e.toString());
        }
        return null;
    }

    /**
     * Returns enrolled courses of given user.
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<EnrolledCoursesResponse> getEnrolledCourses()
            throws Exception {
        return getEnrolledCourses(false);
    }

    /**
     * Returns course identified by given id from cache, null if not course is found.
     * @param courseId
     * @return
     */
    @Override
    public CourseEntry getCourseById(String courseId) {
        try {
            for (EnrolledCoursesResponse r : getEnrolledCourses(true)) {
                if (r.getCourse().getId().equals(courseId)) {
                    return r.getCourse();
                }
            }
        } catch(Exception ex) {
            //logger.error(ex);
            Log.e("Exception in api",ex.toString());
        }

        return null;
    }

    /**
     * Returns list of videos in a particular course.
     * @param courseId
     * @param preferCache
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VideoResponseModel> getVideosByCourseId(String courseId, boolean preferCache)
            throws Exception {
        Bundle p = new Bundle();
        p.putString("format", "json");
        String url = getBaseUrl() + "/api/mobile/v0.5/video_outlines/courses/" + courseId;
        String json = null;
        if (NetworkUtil.isConnected(mContext) && !preferCache) {
            // get data from server
            json = http.get(HttpManager.toGetUrl(url, p), getAuthHeaders());
            // cache the response
            mCache.put(url, json);
        } else {
            json = mCache.get(url);
        }
        //logger.debug("videos_by_course=" + json);

        Gson gson = new GsonBuilder().create();
        TypeToken<ArrayList<VideoResponseModel>> t = new TypeToken<ArrayList<VideoResponseModel>>() {
        };

        ArrayList<VideoResponseModel> list = gson.fromJson(json, t.getType());

        return list;
    }

    /**
     * Returns handout for the given course id.
     * @param url
     * @param preferCache
     * @return
     * @throws Exception
     */
    @Override
    public HandoutModel getHandout(String url, boolean preferCache) throws Exception {
        Bundle p = new Bundle();
        p.putString("format", "json");

        String json = null;
        if (NetworkUtil.isConnected(mContext) && !preferCache) {
            // get data from server
            String urlWithAppendedParams = HttpManager.toGetUrl(url, p);
            //logger.debug("Url "+urlWithAppendedParams);
            json = http.get(urlWithAppendedParams, getAuthHeaders());
            // cache the response
            mCache.put(url, json);
        } else {
            json = mCache.get(url);
        }

        if (json == null) {
            return null;
        }
        //logger.debug("handout=" + json);

        Gson gson = new GsonBuilder().create();
        HandoutModel res = gson.fromJson(json, HandoutModel.class);
        return res;
    }

    /**
     * Returns course info object from the given URL.
     * @param url
     * @param preferCache
     * @return
     * @throws Exception
     */
    @Override
    public CourseInfoModel getCourseInfo(String url, boolean preferCache) throws Exception {
        Bundle p = new Bundle();
        p.putString("format", "json");

        String json = null;
        if (NetworkUtil.isConnected(mContext) && !preferCache) {
            // get data from server
            String urlWithAppendedParams = HttpManager.toGetUrl(url, p);
            //logger.debug("Url "+urlWithAppendedParams);
            json = http.get(urlWithAppendedParams, getAuthHeaders());
            // cache the response
            mCache.put(url, json);
        } else {
            json = mCache.get(url);
        }

        if (json == null) {
            return null;
        }
        //logger.debug("Response of course_about= " + json);

        Gson gson = new GsonBuilder().create();
        CourseInfoModel res = gson.fromJson(json, CourseInfoModel.class);
        return res;
    }

    /**
     * Returns list of announcements for the given course id.
     * @param url
     * @param preferCache
     * @return
     * @throws Exception
     */
    @Override
    public List<AnnouncementsModel> getAnnouncement(String url, boolean preferCache)
            throws Exception {
        Bundle p = new Bundle();
        p.putString("format", "json");
        String json = null;
        if (NetworkUtil.isConnected(mContext) && !preferCache) {
            // get data from server
            String urlWithAppendedParams = HttpManager.toGetUrl(url, p);
            //logger.debug("url : "+urlWithAppendedParams);
            json = http.get(urlWithAppendedParams, getAuthHeaders());
            // cache the response
            mCache.put(url, json);
        } else {
            json = mCache.get(url);
        }

        if (json == null) {
            return null;
        }

        Gson gson = new GsonBuilder().create();
        TypeToken<List<AnnouncementsModel>> t = new TypeToken<List<AnnouncementsModel>>() {
        };
        List<AnnouncementsModel> list = gson.fromJson(json, t.getType());

        return list;
    }

    /**
     * Returns Stream object from the given URL.
     * @param url
     * @param preferCache
     * @return
     * @throws Exception
     */
    @Override
    public CourseInfoModel srtStream(String url, boolean preferCache) throws Exception {
        Bundle p = new Bundle();
        p.putString("format", "json");

        String json = null;
        if (NetworkUtil.isConnected(mContext) && !preferCache) {
            // get data from server
            String urlWithAppendedParams = HttpManager.toGetUrl(url, p);
            //logger.debug("Url "+urlWithAppendedParams);
            json = http.get(urlWithAppendedParams, getAuthHeaders());
            // cache the response
            //cache.put(url, json);
        } else {
            json = mCache.get(url);
        }

        if (json == null) {
            return null;
        }
        //logger.debug("srt stream= " + json);

        Gson gson = new GsonBuilder().create();
        CourseInfoModel res = gson.fromJson(json, CourseInfoModel.class);
        return res;
    }

    /**
     * Returns Transcript of a given Video.
     *
     * @param
     * @return TranscriptModel
     * @throws Exception
     */
    @Override
    public TranscriptModel getTranscriptsOfVideo(String enrollmentId,
                                                 String videoId) throws Exception {
        try{
            TranscriptModel transcript;
            VideoResponseModel vidModel =  getVideoById(enrollmentId, videoId);
            if(vidModel!=null){
                if(vidModel.getSummary()!=null){
                    transcript = vidModel.getSummary().getTranscripts();
                    return transcript;
                }
            }
        }catch(Exception e){
            //logger.error(e);
        }
        return null;
    }

    @Override
    public String downloadTranscript(String url)
            throws Exception {
        if (url != null){
            try {
                if (NetworkUtil.isConnected(this.mContext)) {
                    String str = http.get(url, getAuthHeaders());
                    return str;
                }
            } catch (Exception ex){
                //logger.error(ex);
            }
        }
        return null;
    }

    /**
     * Returns list of videos for a particular URL.
     * @param courseId
     * @param preferCache
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VideoResponseModel> getVideosByURL(String courseId, String videoUrl, boolean preferCache)
            throws Exception {
        if(videoUrl==null){
            return null;
        }
        ArrayList<VideoResponseModel> vidList = getVideosByCourseId(courseId, preferCache);
        ArrayList<VideoResponseModel> list = new ArrayList<VideoResponseModel>();
        if(vidList!=null && vidList.size()>0){
            for(VideoResponseModel vrm : vidList){
                try{
                    if(vrm.getSummary().getVideo_url().equalsIgnoreCase(videoUrl)){
                        vrm.setCourseId(courseId);
                        list.add(vrm);
                    }
                }catch(Exception e){
                    //logger.error(e);
                }
            }
        }

        return list;
    }

    /**
     * Returns chapter model and the subsequent sections and videos in organized manner from cache.
     * @param courseId
     * @param chapter
     * @return
     */
    @Override
    public ArrayList<SectionItemInterface> getLiveOrganizedVideosByChapter
    (String courseId, String chapter) {

        ArrayList<SectionItemInterface> list = new ArrayList<SectionItemInterface>();

        // add chapter to the result
        ChapterModel c = new ChapterModel();
        c.name = chapter;
        list.add(c);

        try {
            HashMap<String, ArrayList<VideoResponseModel>> sections =
                    new LinkedHashMap<String, ArrayList<VideoResponseModel>>();

            ArrayList<VideoResponseModel> videos = getVideosByCourseId(courseId, true);
            for (VideoResponseModel v : videos) {
                // filter videos by chapter
                if (v.getChapter().name.equals(chapter)) {
                    // this video is under the specified chapter

                    // sort out the section of this video
                    if (sections.containsKey(v.getSection().name)) {
                        ArrayList<VideoResponseModel> sv = sections.get(v.getSection().name);
                        if (sv == null) {
                            sv = new ArrayList<VideoResponseModel>();
                        }
                        sv.add(v);
                    } else {
                        ArrayList<VideoResponseModel> vlist = new ArrayList<VideoResponseModel>();
                        vlist.add(v);
                        sections.put(v.getSection().name, vlist);
                    }
                }
            }

            // now add sectioned videos to the result
            for (Map.Entry<String, ArrayList<VideoResponseModel>> entry : sections.entrySet()) {
                // add section to the result
                SectionItemModel s = new SectionItemModel();
                s.name = entry.getKey();
                list.add(s);

                // add videos to the result
                if (entry.getValue() != null) {
                    for (VideoResponseModel v : entry.getValue()) {
                        list.add(v);
                    }
                }
            }
        } catch (Exception e) {
            //logger.error(e);
        }

        return list;
    }

    @Override
    public SyncLastAccessedSubsectionResponse syncLastAccessedSubsection(String courseId,
                                                                         String lastVisitedModuleId) throws Exception {

        PrefManager pref = new PrefManager(mContext, PrefManager.Pref.LOGIN);
        String username = pref.getCurrentUserProfile().username;

        String url = getBaseUrl() + "/api/mobile/v0.5/users/" + username + "/course_status_info/" + courseId;
        //logger.debug("PATCH url for syncLastAccessed Subsection: " + url);

        String date = DateUtil.getModificationDate();

        JSONObject postBody = new JSONObject();
        postBody.put("last_visited_module_id", lastVisitedModuleId);
        postBody.put("modification_date", date);

        //logger.debug("PATCH body for syncLastAccessed Subsection: " + postBody.toString());
        String json = http.post(url, postBody.toString(), getAuthHeaders(), true);

        if (json == null) {
            return null;
        }
        //logger.debug("Response of sync last viewed= " + json);

        Gson gson = new GsonBuilder().create();
        SyncLastAccessedSubsectionResponse res = gson.fromJson(json, SyncLastAccessedSubsectionResponse.class);

        return res;
    }

    @Override
    public SyncLastAccessedSubsectionResponse getLastAccessedSubsection(String courseId) throws Exception {
        PrefManager pref = new PrefManager(mContext, PrefManager.Pref.LOGIN);
        String username = pref.getCurrentUserProfile().username;

        String url = getBaseUrl() + "/api/mobile/v0.5/users/" + username + "/course_status_info/" + courseId;
        //logger.debug("Url of get last accessed subsection: " + url);

        String date = DateUtil.getModificationDate();

        String json = http.get(url, getAuthHeaders());

        if (json == null) {
            return null;
        }
        //logger.debug("Response of get last viewed subsection.id = " + json);

        Gson gson = new GsonBuilder().create();
        SyncLastAccessedSubsectionResponse res = gson.fromJson(json, SyncLastAccessedSubsectionResponse.class);

        return res;
    }

    /**
     * Creates new account.
     * @param parameters
     * @return
     * @throws Exception
     */
    @Override
    public RegisterResponse register(Bundle parameters)
            throws Exception {
        String url = getBaseUrl() + "/user_api/v1/account/registration/";

        String json = http.post(url, parameters, null);

        if (json == null) {
            return null;
        }
        //logger.debug("Register response= " + json);

        //the server side response format is not client friendly ... so..
        Gson gson = new GsonBuilder().create();
        try {
            FormFieldMessageBody body = gson.fromJson(json, FormFieldMessageBody.class);
            if( body != null && body.size() > 0 ){
                RegisterResponse res = new RegisterResponse();
                res.setMessageBody(body);
                return res;
            }
        }catch (Exception ex){
            //normal workflow , ignore it.
        }
        RegisterResponse res = gson.fromJson(json, RegisterResponse.class);

        return res;
    }

    /**
     * Reads registration description from assets and return Model representation of it.
     * @return
     * @throws Exception
     */
    @Override
    public RegistrationDescription getRegistrationDescription() throws Exception {
        Gson gson = new Gson();
        InputStream in = mContext.getAssets().open("config/registration_form.json");
        RegistrationDescription form = gson.fromJson(new InputStreamReader(in), RegistrationDescription.class);
        //logger.debug("picking up registration description (form) from assets, not from cache");
        return form;
    }

    @Override
    public Boolean enrollInACourse(String courseId, boolean email_opt_in) throws Exception {
        String enrollUrl = getBaseUrl() + "/api/enrollment/v1/enrollment";
        //logger.debug("POST url for enrolling in a Course: " + enrollUrl);

        JSONObject postBody = new JSONObject();
        JSONObject courseIdObject = new JSONObject();
        courseIdObject.put("email_opt_in", email_opt_in);
        courseIdObject.put("course_id", courseId);
        postBody.put("course_details", courseIdObject);

        //logger.debug("POST body for Enrolling in a course: " + postBody.toString());
        String json = http.post(enrollUrl, postBody.toString(), getAuthHeaders(), false);

        if (json != null && !json.isEmpty()) {
            //logger.debug("Response of Enroll in a course= " + json);
            JSONObject resultJson = new JSONObject(json);
            if (resultJson.has("error")) {
                return false;
            }else {
                return true;
            }
        }

        return false;
    }
}
