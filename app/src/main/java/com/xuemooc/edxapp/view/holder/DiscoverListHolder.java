package com.xuemooc.edxapp.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * Created by chaossss on 2015/7/29.
 */
public class DiscoverListHolder extends RecyclerView.ViewHolder {
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

    public void setInfo(String courseName,String school,int watchNums,String time){
        this.time.setText(time);
        this.school.setText(school);
        this.watchNums.setText(watchNums + "");
        this.courseName.setText(courseName);
    }
}
