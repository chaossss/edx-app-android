package com.xuemooc.edxapp.model.data;

/**
 * Created by chaossss on 2015/7/29.
 */
public class CourseModel {
    private String time;
    private String school;
    private String courseName;

    private int watchNums;

    public String getTime() {
        return time;
    }

    public String getSchool() {
        return school;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getWatchNums() {
        return watchNums;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setWatchNums(int watchNums) {
        this.watchNums = watchNums;
    }
}
