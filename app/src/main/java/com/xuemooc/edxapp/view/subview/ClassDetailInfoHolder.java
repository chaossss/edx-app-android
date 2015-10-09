package com.xuemooc.edxapp.view.subview;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * Created by chaossss on 2015/10/9.
 */
public class ClassDetailInfoHolder extends RecyclerView.ViewHolder {
    private TextView courseName;
    private TextView schoolName;
    private TextView teacherName;
    private TextView courseTime;
    private TextView courseFrequency;
    private TextView courseDescription;

    private ImageView teacherImg;

    public ClassDetailInfoHolder(View itemView) {
        super(itemView);

        courseName = (TextView) itemView.findViewById(R.id.class_detail_course_name);
        schoolName = (TextView) itemView.findViewById(R.id.class_detail_school_name);
        teacherName = (TextView) itemView.findViewById(R.id.class_detail_teacher_name);
        courseTime = (TextView) itemView.findViewById(R.id.class_detail_course_time);
        courseFrequency = (TextView) itemView.findViewById(R.id.class_detail_frequency);
        courseDescription = (TextView) itemView.findViewById(R.id.class_detail_course_description);

        teacherImg = (ImageView) itemView.findViewById(R.id.class_detail_teacher_img);
    }

    public void setClassDetailInfo(String courseName, String schoolName, String teacherName
            , String courseTime, String courseFrequency, String courseDescription){
        this.courseName.setText(courseName);
        this.schoolName.setText(schoolName);
        this.teacherName.setText(teacherName);
        this.courseTime.setText(courseTime);
        this.courseFrequency.setText(courseFrequency);
        this.courseDescription.setText(courseDescription);
    }

    public void setTeacherImg(Bitmap bitmap){
        this.teacherImg.setImageBitmap(bitmap);
    }
}
