package com.xuemooc.edxapp.view.subview;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * 我的课程列表数据项的布局
 * Created by chaossss on 2015/7/30.
 */
public class MyCourseListHolder extends RecyclerView.ViewHolder {
    private TextView school;
    private TextView courseName;
    private TextView updateInfo;
    private ImageView courseImage;

    public MyCourseListHolder(View itemView) {
        super(itemView);

        school = (TextView) itemView.findViewById(R.id.my_course_list_school);
        courseName = (TextView) itemView.findViewById(R.id.my_course_list_name);
        updateInfo = (TextView) itemView.findViewById(R.id.my_course_list_update);
        courseImage = (ImageView) itemView.findViewById(R.id.my_course_list_img);
    }

    /**
     * 填入我的课程数据
     * @param courseName 课程名
     * @param school 课程开设学校
     * @param updateInfo 课程最新信息
     */
    public void setInfo(String courseName,String school,String updateInfo){
        this.school.setText(school);
        this.courseName.setText(courseName);
        this.updateInfo.setText(updateInfo);
    }

    public void setImage(Bitmap img){
        courseImage.setImageBitmap(img);
    }
}
