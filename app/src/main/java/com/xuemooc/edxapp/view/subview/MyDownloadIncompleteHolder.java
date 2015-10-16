package com.xuemooc.edxapp.view.subview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.adapter.MyDownloadIncompleteItemAdapter;

import java.util.List;

/**
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView courseName;
    private RecyclerView courseItemList;
    private RelativeLayout downloadingCourse;
    private MyDownloadIncompleteItemAdapter adapter;

    private List<String> incompleteItemDatas;

    public MyDownloadIncompleteHolder(View itemView) {
        super(itemView);

        courseName = (TextView) itemView.findViewById(R.id.my_download_incomplete_course_name_text);
        courseItemList = (RecyclerView) itemView.findViewById(R.id.my_download_incomplete_course_item_list);
        downloadingCourse = (RelativeLayout) itemView.findViewById(R.id.my_download_incomplete_course_name);

        downloadingCourse.setOnClickListener(this);

        courseItemList.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }

    public List<String> getIncompleteItemList() {
        return incompleteItemDatas;
    }

    public void setIncompleteItemList(List<String> incompleteItemDatas) {
        this.incompleteItemDatas = incompleteItemDatas;

        if(incompleteItemDatas.size() > 0){
            courseName.setText(incompleteItemDatas.get(0));
            this.incompleteItemDatas.remove(0);
        }

        adapter = new MyDownloadIncompleteItemAdapter(this.incompleteItemDatas);
        courseItemList.setAdapter(adapter);
    }

    public void addIncompleteItem(String item){
        incompleteItemDatas.add(item);
    }

    @Override
    public void onClick(View v) {

    }
}
