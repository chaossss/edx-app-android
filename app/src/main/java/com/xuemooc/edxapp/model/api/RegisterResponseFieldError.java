package com.xuemooc.edxapp.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hackeris on 15/8/3.
 */
public class RegisterResponseFieldError {

    private @SerializedName("user_message") String userMessage;

    public String getUserMessage() {
        return userMessage;
    }
}
