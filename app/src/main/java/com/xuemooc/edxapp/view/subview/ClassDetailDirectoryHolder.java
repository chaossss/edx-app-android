package com.xuemooc.edxapp.view.subview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * Created by chaossss on 2015/10/10.
 */
public class ClassDetailDirectoryHolder extends RecyclerView.ViewHolder {
    private TextView courseName;
    private TextView courseDuration;

    public ClassDetailDirectoryHolder(View itemView) {
        super(itemView);

        courseName = (TextView) itemView.findViewById(R.id.class_detail_directory_course_name);
        courseDuration = (TextView) itemView.findViewById(R.id.class_detail_directory_course_duration);
    }

    public void setClassDetailDirectoryInfo(String courseName, String courseDuration){
        this.courseName.setText(courseName);
        this.courseDuration.setText(courseDuration);
    }
}
