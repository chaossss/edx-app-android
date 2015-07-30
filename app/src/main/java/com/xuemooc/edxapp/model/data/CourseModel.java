package com.xuemooc.edxapp.model.data;

/**
 * 课程数据模型基类
 * Created by chaossss on 2015/7/30.
 */
public class CourseModel {
    protected String imgUrl;
    protected String courseName;
    protected String school;

    public CourseModel() {
    }

    /**
     * @param courseName 课程名称
     * @param school 课程开设学校
     * @param imgUrl 课程图片
     */
    public CourseModel(String courseName, String school, String imgUrl) {
        this.school = school;
        this.imgUrl = imgUrl;
        this.courseName = courseName;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchool() {
        return school;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCourseName() {
        return courseName;
    }
}
