package com.xuemooc.edxapp.model.data;

/**
 * 发现课程数据模型类
 * Created by chaossss on 2015/7/29.
 */
public class DiscoverCourseModel extends CourseModel{
    private String time;
    private int watchNums;

    public DiscoverCourseModel() {
    }

    /**
     * @param courseName 课程名称
     * @param school 课程开设学校
     * @param imgUrl 课程图片
     * @param watchNums 参与人数
     * @param time 课程开设时间
     */
    public DiscoverCourseModel(String courseName, String school, String imgUrl, int watchNums, String time){
        super(courseName, school, imgUrl);

        this.time = time;
        this.watchNums = watchNums;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWatchNums() {
        return watchNums;
    }

    public void setWatchNums(int watchNums) {
        this.watchNums = watchNums;
    }
}
