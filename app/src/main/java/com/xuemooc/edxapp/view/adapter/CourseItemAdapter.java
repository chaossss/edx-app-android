package com.xuemooc.edxapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.model.data.CourseModel;
import com.xuemooc.edxapp.view.holder.DiscoverListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaossss on 2015/7/30.
 */
public class CourseItemAdapter extends RecyclerView.Adapter<DiscoverListHolder> {
    private List<CourseModel> courseList = new ArrayList<>();

    public CourseItemAdapter(List<CourseModel> courseList) {
        super();
        this.courseList = courseList;
    }

    public void addCourse(CourseModel course){
        courseList.add(course);
    }

    @Override
    public DiscoverListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_list, parent, false);
        return new DiscoverListHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverListHolder holder, int position)
    {
        CourseModel course = courseList.get(position);
        holder.setInfo(course.getCourseName(),course.getSchool(),course.getWatchNums(),course.getTime());
    }

    @Override
    public int getItemCount()
    {
        return courseList.size();
    }
}
