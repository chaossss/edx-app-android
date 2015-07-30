package com.xuemooc.edxapp.model.data;

/**
 * ���ֿγ�����ģ����
 * Created by chaossss on 2015/7/29.
 */
public class DiscoverCourseModel extends CourseModel{
    private String time;
    private int watchNums;

    public DiscoverCourseModel() {
    }

    /**
     * @param courseName �γ�����
     * @param school �γ̿���ѧУ
     * @param imgUrl �γ�ͼƬ
     * @param watchNums ��������
     * @param time �γ̿���ʱ��
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
