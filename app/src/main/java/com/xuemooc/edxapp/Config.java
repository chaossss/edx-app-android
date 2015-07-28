package com.xuemooc.edxapp;

/**
 * Created by hackeris on 15/7/28.
 */
public class Config {

    private static final String BASE_URL = "http://lms.geekmooc.com";

    private static final String CLIENT_ID = "";

    private static final String CLIENT_SECRET = "";

    private static Config configInstance;

    public static Config getInstance(){
        if(configInstance == null){
            configInstance = new Config();
        }
        return configInstance;
    }

    public String getBaseUrl(){

        return BASE_URL;
    }

    public String getClientId(){

        return CLIENT_ID;
    }

    public String getClientSecret(){

        return CLIENT_SECRET;
    }
}
