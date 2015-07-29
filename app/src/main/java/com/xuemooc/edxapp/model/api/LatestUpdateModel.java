package com.xuemooc.edxapp.model.api;

import java.io.Serializable;

/**
 * Created by hackeris on 15/7/29.
 */
@SuppressWarnings("serial")
public class LatestUpdateModel implements Serializable {
    private String video;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

}
