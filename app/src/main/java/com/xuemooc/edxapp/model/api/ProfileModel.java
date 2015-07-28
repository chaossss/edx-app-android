package com.xuemooc.edxapp.model.api;

import java.io.Serializable;

/**
 * Created by hackeris on 15/7/28.
 */
public class ProfileModel implements Serializable {

    public Long id;
    public String username;
    public String email;
    public String name;
    public String course_enrollments;
    // public String url;

    // hold the json response as it is
    public String json;
}
