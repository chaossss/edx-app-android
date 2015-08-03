package com.xuemooc.edxapp.model.api;

import java.io.Serializable;

/**
 * Created by hackeris on 15/8/3.
 */
@SuppressWarnings("serial")
public class PathModel implements Serializable {

    public String category;
    public String name;
    public String id;

    /*
     * Returns true if this is a CHAPTER.
     */
    public boolean isChapter() {
        return category.equalsIgnoreCase("chapter");
    }

    /**
     * Returns true if this is a SECTION.
     * @return
     */
    public boolean isSequential() {
        return category.equalsIgnoreCase("sequential");
    }

    /*
     * Returns true if this is a VERTICAL.
     * This is not to be used in the mobile app.
     */
    private boolean isVertical() {
        return category.equalsIgnoreCase("vertical");
    }
}