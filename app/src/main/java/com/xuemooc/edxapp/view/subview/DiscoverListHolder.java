package com.xuemooc.edxapp.view.subview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * 发现课程列表项布局
 * Created by chaossss on 2015/7/29.
 */
public class DiscoverListHolder extends RecyclerView.ViewHolder{
    private TextView time = null;
    private TextView school = null;
    private TextView watchNums = null;
    private TextView courseName = null;

    public DiscoverListHolder(View itemView) {
        super(itemView);

        time = (TextView) itemView.findViewById(R.id.discover_list_time);
        school = (TextView) itemView.findViewById(R.id.discover_list_school);
        watchNums = (TextView) itemView.findViewById(R.id.discover_list_watch);
        courseName = (TextView) itemView.findViewById(R.id.discover_list_name);
    }

    /**
     * 填入发现课程数据
     * @param courseName 课程名
     * @param school 课程开设学校
     * @param watchNums 课程观看人数
     * @param time 课程开设时间
     */
    public void setInfo(String courseName,String school,int watchNums,String time){
        this.time.setText(time);
        this.school.setText(school);
        this.watchNums.setText(watchNums + "");
        this.courseName.setText(courseName);
    }
}
