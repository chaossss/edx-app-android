package com.xuemooc.edxapp.model.api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hackeris on 15/8/3.
 */
@SuppressWarnings("serial")
public class LectureModel extends ResponseStatus implements Serializable {

    public String name;
    public ArrayList<VideoResponseModel> videos;

    /**
     * Chapter object to carry with Lecture for easy access.
     * Sometimes we need chapter name for this lecture.
     */
    public SectionEntry chapter;
}

