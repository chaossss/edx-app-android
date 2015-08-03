package com.xuemooc.edxapp.model.api;

/**
 * Created by hackeris on 15/8/3.
 */
public class SectionItemModel implements SectionItemInterface {

    public String name;

    @Override
    public boolean isChapter() {
        return false;
    }

    @Override
    public boolean isSection() {
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isCourse() {
        return false;
    }

    @Override
    public boolean isVideo() {
        return false;
    }

    @Override
    public boolean isDownload() {
        return false;
    }
}

