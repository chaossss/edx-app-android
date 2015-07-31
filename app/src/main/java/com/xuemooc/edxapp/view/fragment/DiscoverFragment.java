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
import com.xuemooc.edxapp.model.data.DiscoverCourseModel;
import com.xuemooc.edxapp.view.adapter.DiscoverListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现课程页面
 * Created by chaossss on 2015/7/30.
 */
public class DiscoverFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView discoverListView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private DiscoverListAdapter adapter;
    private List<DiscoverCourseModel> discoverList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_discover, container, false);

        discoverListView=(RecyclerView) root.findViewById(R.id.discover_list);
        swipeRefreshLayout=(SwipeRefreshLayout) root.findViewById(R.id.discover_swipe);
        discoverListView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        for(int i = 0;i < 5;++i)
        {
            DiscoverCourseModel temp = new DiscoverCourseModel("课程" + i, "学校" + i,"", i, "时间" + i);
            discoverList.add(temp);
        }
        adapter = new DiscoverListAdapter(discoverList);
        discoverListView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

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
                    DiscoverCourseModel temp = new DiscoverCourseModel("new课程" + i, "new学校" + i,"", i, "new时间" + i);
                    discoverList.add(temp);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }

    @Override
    public void onRefresh() {
        refreshContent();
    }
}
