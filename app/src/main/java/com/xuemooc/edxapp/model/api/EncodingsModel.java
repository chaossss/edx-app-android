package com.xuemooc.edxapp.model.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hackeris on 15/8/3.
 */
public class EncodingsModel implements Serializable {

    @SerializedName("high")
    public String highEncoding;

    @SerializedName("low")
    public String lowEncoding;

    @SerializedName("youtube")
    public String youtubeLink;

    public enum EncodingLevel{
        HIGH,
        LOW,
    }
}