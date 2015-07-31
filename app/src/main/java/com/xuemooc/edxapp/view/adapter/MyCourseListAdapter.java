package com.xuemooc.edxapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.model.data.MyCourseModel;
import com.xuemooc.edxapp.view.holder.MyCourseListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaossss on 2015/7/30.
 */
public class MyCourseListAdapter extends RecyclerView.Adapter<MyCourseListHolder> {
    private List<MyCourseModel> courseList = new ArrayList<>();

    /**
     * @param courseList 我的课程列表
     */
    public MyCourseListAdapter(List<MyCourseModel> courseList) {
        super();
        this.courseList = courseList;
    }

    /**
     * 向我的课程列表添加课程
     * @param course 新增课程
     */
    public void addCourse(MyCourseModel course){
        courseList.add(course);
    }

    public MyCourseModel removeCourse(int pos){
        return courseList.remove(pos);
    }

    @Override
    public MyCourseListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_course_list, parent, false);
        return new MyCourseListHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCourseListHolder holder, int position)
    {
        MyCourseModel course = courseList.get(position);
        holder.setInfo(course.getCourseName(),course.getSchool(),course.getUpdateInfo());
    }

    @Override
    public int getItemCount()
    {
        return courseList.size();
    }
}
