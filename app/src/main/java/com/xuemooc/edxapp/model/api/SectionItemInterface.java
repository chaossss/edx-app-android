package com.xuemooc.edxapp.model.api;

import java.io.Serializable;

/**
 * Created by hackeris on 15/7/29.
 */
public interface SectionItemInterface extends Serializable {

    public boolean isCourse();
    public boolean isChapter();
    public boolean isSection();
    public boolean isVideo();
    public boolean isDownload();
}
