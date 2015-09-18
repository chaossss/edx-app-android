package com.xuemooc.edxapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.model.data.DiscoverCourseModel;
import com.xuemooc.edxapp.view.holder.DiscoverListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现课程列表的数据填充器
 * Created by chaossss on 2015/7/30.
 */
public class DiscoverListAdapter extends RecyclerView.Adapter<DiscoverListHolder> implements View.OnClickListener{
    private OnItemClickListener listener;
    private List<DiscoverCourseModel> courseList = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(View v);
    }

    /**
     * @param courseList 发现课程列表的数据项
     */
    public DiscoverListAdapter(List<DiscoverCourseModel> courseList) {
        super();
        this.courseList = courseList;
    }

    /**
     * 添加新增课程
     * @param course
     */
    public void addCourse(DiscoverCourseModel course){
        courseList.add(course);
    }

    @Override
    public DiscoverListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_list, parent, false);
        view.setOnClickListener(this);
        return new DiscoverListHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverListHolder holder, int position)
    {
        DiscoverCourseModel course = courseList.get(position);
        holder.setInfo(course.getCourseName(),course.getSchool(),course.getWatchNums(),course.getTime());
    }

    @Override
    public int getItemCount()
    {
        return courseList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v);
    }
}
