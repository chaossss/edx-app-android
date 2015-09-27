package com.xuemooc.edxapp.utils.util;

/**
 * Created by hackeris on 15/7/28.
 */
public class Config {

    private static final String BASE_URL = "http://lms.geekmooc.com";

    private static final String CLIENT_ID = "95ecc036ec9c74cfbf49";

    private static final String CLIENT_SECRET = "4c89ff3332f048d85930fb4db1c1cbb239422969";

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
