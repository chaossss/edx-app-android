package com.xuemooc.edxapp.model.data;

/**
 * 我的课程数据模型基类
 * Created by chaossss on 2015/7/30.
 */
public class MyCourseModel extends CourseModel{
    private String updateInfo;

    public MyCourseModel() {
    }

    /**
     * @param courseName 课程名
     * @param school 课程开设学校
     * @param imgUrl 课程图片
     * @param updateInfo 课程最新信息
     */
    public MyCourseModel(String courseName, String school, String imgUrl, String updateInfo) {
        super(courseName, school, imgUrl);

        this.updateInfo = updateInfo;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }
}
