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
import com.xuemooc.edxapp.model.data.CourseModel;
import com.xuemooc.edxapp.view.adapter.CourseItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaossss on 2015/7/30.
 */
public class DiscoverFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CourseItemAdapter adapter;
    private List<CourseModel> discoverList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_discover, container, false);

        recyclerView=(RecyclerView) root.findViewById(R.id.discover_list);
        swipeRefreshLayout=(SwipeRefreshLayout) root.findViewById(R.id.discover_swipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        for(int i = 0;i < 5;++i)
        {
            CourseModel temp = new CourseModel();
            temp.setWatchNums(i);
            temp.setTime(i + "");
            temp.setSchool(i + "");
            temp.setCourseName(i + "");
            discoverList.add(temp);
        }
        adapter = new CourseItemAdapter(discoverList);
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
                    CourseModel temp = new CourseModel();
                    temp.setWatchNums(i);
                    temp.setTime(i + "new");
                    temp.setSchool(i + "new");
                    temp.setCourseName(i + "new");
                    discoverList.add(temp);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }
}
