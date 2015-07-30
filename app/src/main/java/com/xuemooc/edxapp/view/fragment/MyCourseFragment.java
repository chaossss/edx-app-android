package com.xuemooc.edxapp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.model.data.MyCourseModel;
import com.xuemooc.edxapp.view.adapter.MyCourseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的课程页面
 * Created by chaossss on 2015/7/30.
 */
public class MyCourseFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MyCourseListAdapter adapter;
    private List<MyCourseModel> myCourseList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_course, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.my_course_list);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.my_course_swipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        for(int i = 0;i < 5;++i)
        {
            MyCourseModel temp = new MyCourseModel("课程" + i, "学校" + i, "", "更新信息" + i);
            myCourseList.add(temp);
        }
        adapter = new MyCourseListAdapter(myCourseList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        return root;
    }

    private void refreshContent()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                for(int i=0;i<5;++i)
                {
                    MyCourseModel temp = new MyCourseModel("new课程" + i, "new学校" + i, "", "new更新信息" + i);
                    myCourseList.add(temp);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }
}
