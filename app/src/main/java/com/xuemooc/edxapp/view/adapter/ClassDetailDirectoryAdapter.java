package com.xuemooc.edxapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.model.data.CourseDirectoryModel;
import com.xuemooc.edxapp.view.subview.ClassDetailDirectoryHolder;

import java.util.List;

/**
 * Created by chaossss on 2015/10/10.
 */
public class ClassDetailDirectoryAdapter extends RecyclerView.Adapter<ClassDetailDirectoryHolder> {
    private List<CourseDirectoryModel> data;

    public ClassDetailDirectoryAdapter(List<CourseDirectoryModel> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ClassDetailDirectoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_detail_directory, parent, false);
        return new ClassDetailDirectoryHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassDetailDirectoryHolder holder, int position) {
        holder.setClassDetailDirectoryInfo(data.get(position).getCourseName(), data.get(position).getCourseDuration());
    }
}
