package com.xuemooc.edxapp.view.subview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

import java.util.List;

/**
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView courseName;
    private RelativeLayout downloadingCourse;

    private List<String> incompleteItemDatas;

    public MyDownloadIncompleteHolder(View itemView) {
        super(itemView);

        courseName = (TextView) itemView.findViewById(R.id.my_download_incomplete_course_name_text);
        downloadingCourse = (RelativeLayout) itemView.findViewById(R.id.my_download_incomplete_course_name);
    }

    public List<String> getIncompleteItemList() {
        return incompleteItemDatas;
    }

    public void setCourseName(String courseName){
        this.courseName.setText(courseName);
    }

    public void setIncompleteItemList(List<String> incompleteItemDatas) {
        this.incompleteItemDatas = incompleteItemDatas;

        if(incompleteItemDatas.size() > 0){
            courseName.setText(incompleteItemDatas.get(0));
            this.incompleteItemDatas.remove(0);
        }
    }

    public void addIncompleteItem(String item){
        incompleteItemDatas.add(item);
    }

    @Override
    public void onClick(View v) {

    }
}
