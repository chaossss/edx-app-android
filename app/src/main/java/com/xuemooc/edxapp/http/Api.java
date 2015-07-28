package com.xuemooc.edxapp.http;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuemooc.edxapp.Config;
import com.xuemooc.edxapp.model.api.AuthResponse;
import com.xuemooc.edxapp.model.api.ProfileModel;
import com.xuemooc.edxapp.model.api.ResetPasswordResponse;
import com.xuemooc.edxapp.module.prefs.PrefManager;

import java.util.List;
import java.util.Map;

/**
 * Created by hackeris on 15/7/28.
 */
public class Api {

    private Context mContext;

    private HttpManager http;

    public String getBaseUrl() {
        return Config.getInstance().getBaseUrl();
    }

    public Api(Context context) {
        this.mContext = context;
        this.http = new HttpManager();
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
     * Resets password for the given email id.
     * @param emailId
     * @return
     * @throws Exception
     */
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
}
