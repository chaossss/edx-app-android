package com.xuemooc.edxapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.model.data.CourseDirectoryModel;
import com.xuemooc.edxapp.view.adapter.ClassDetailDirectoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaossss on 2015/10/9.
 */
public class ClassDetailDirectoryFragment extends Fragment {
    private RecyclerView scrollView;
    private ClassDetailDirectoryAdapter adapter;
    private List<CourseDirectoryModel> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_detail_list, container, false);

        scrollView = (RecyclerView) rootView.findViewById(R.id.class_detail_list);
        scrollView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        for(int i = 0; i < 5; i++){
            CourseDirectoryModel temp = new CourseDirectoryModel();
            temp.setCourseName("课程 " + i);
            temp.setCourseDuration("时长 " + i);
            data.add(temp);
        }

        adapter = new ClassDetailDirectoryAdapter(data);

        scrollView.setAdapter(adapter);
        return rootView;
    }
}
