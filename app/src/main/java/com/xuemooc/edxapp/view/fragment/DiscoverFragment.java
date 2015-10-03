package com.xuemooc.edxapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrengineer13.snackbar.SnackBar;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.model.data.DiscoverCourseModel;
import com.xuemooc.edxapp.view.activity.ClassDetailActivity;
import com.xuemooc.edxapp.view.adapter.DiscoverListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Discover Course page
 *
 * Created by chaossss on 2015/7/30.
 */
public class DiscoverFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, DiscoverListAdapter.OnItemClickListener{
    private RecyclerView discoverListView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private DiscoverListAdapter adapter;
    private List<DiscoverCourseModel> discoverList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_discover, container, false);

        discoverListView = (RecyclerView) root.findViewById(R.id.discover_list);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.discover_swipe);
        discoverListView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        for(int i = 0;i < 5;++i)
        {
            DiscoverCourseModel temp = new DiscoverCourseModel("课程" + i, "学校" + i,"", i, "时间" + i);
            discoverList.add(temp);
        }

        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new DiscoverListAdapter(discoverList);
        discoverListView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        ItemTouchHelper.SimpleCallback swipeDismissCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos = viewHolder.getAdapterPosition();
                final DiscoverCourseModel deletedCourse = adapter.removeCourse(pos);

                adapter.notifyDataSetChanged();

                new SnackBar.Builder(getActivity())
                        .withOnClickListener(new SnackBar.OnMessageClickListener() {
                            /**
                             * undo the delete operation
                             * @param parcelable
                             */
                            @Override
                            public void onMessageClick(Parcelable parcelable) {
                                adapter.addCourse(deletedCourse, pos);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .withMessage("课程：" + deletedCourse.getCourseName() + "已删除")
                        .withActionMessage("撤销")
                        .withStyle(SnackBar.Style.ALERT)
                        .withDuration(SnackBar.MED_SNACK)
                        .show();

            }
        };
        ItemTouchHelper swipeDismissHelper = new ItemTouchHelper(swipeDismissCallback);
        swipeDismissHelper.attachToRecyclerView(discoverListView);

        return root;
    }

    /**
     * When swipe down on the list's top, get the latest data and update the list
     */
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
                    discoverList.add(0, temp);
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

    @Override
    public void onItemClick(View v) {
        startActivity(new Intent(getActivity(), ClassDetailActivity.class));
    }
}
