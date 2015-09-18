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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrengineer13.snackbar.SnackBar;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.model.data.MyCourseModel;
import com.xuemooc.edxapp.view.activity.ClassDetailActivity;
import com.xuemooc.edxapp.view.adapter.MyCourseListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

/**
 * 我的课程页面
 * Created by chaossss on 2015/7/30.
 */
public class MyCourseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyCourseListAdapter.OnItemClickListener{
    private RecyclerView myCourseListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeDismissRecyclerViewTouchListener listener;

    private MyCourseListAdapter adapter;
    private List<MyCourseModel> myCourseList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_course, container, false);

        myCourseListView = (RecyclerView) root.findViewById(R.id.my_course_list);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.my_course_swipe);
        myCourseListView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        for(int i = 0;i < 5;++i)
        {
            MyCourseModel temp = new MyCourseModel("课程" + i, "学校" + i, "", "更新信息" + i);
            myCourseList.add(temp);
        }

        initSwipeDismissListener();
        myCourseListView.setOnTouchListener(listener);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new MyCourseListAdapter(myCourseList);
        myCourseListView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        return root;
    }

    /**
     * 初始化左滑删除监听
     */
    private void initSwipeDismissListener(){
        listener = new SwipeDismissRecyclerViewTouchListener.Builder(myCourseListView,
                new SwipeDismissRecyclerViewTouchListener.DismissCallbacks(){
                    @Override
                    public boolean canDismiss(int i) {
                        return true;
                    }

                    /**
                     * 当 RecyclerView 中 View 项被删除时触发的操作
                     * @param view 被删除项
                     */
                    @Override
                    public void onDismiss(View view) {
                        int pos = myCourseListView.getChildAdapterPosition(view);
                        MyCourseModel deleteCourse = adapter.removeCourse(pos);
                        adapter.notifyDataSetChanged();

                        new SnackBar.Builder(getActivity())
                                .withOnClickListener(new SnackBar.OnMessageClickListener() {
                                    /**
                                     * 撤销删除操作的实现
                                     * @param parcelable
                                     */
                                    @Override
                                    public void onMessageClick(Parcelable parcelable) {

                                    }
                                })
                                .withMessage("课程：" + deleteCourse.getCourseName() + "已删除")
                                .withActionMessage("撤销")
                                .withStyle(SnackBar.Style.ALERT)
                                .withDuration(SnackBar.MED_SNACK)
                                .show();
                    }
                })
                .setIsVertical(false)
                .setItemTouchCallback(
                        new SwipeDismissRecyclerViewTouchListener.OnItemTouchCallBack() {
                            @Override
                            public void onTouch(int i) {

                            }
                        }
                )
                .create();
    }

    /**
     * 刷新数据操作
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
                    MyCourseModel temp = new MyCourseModel("new课程" + i, "new学校" + i, "", "new更新信息" + i);
                    myCourseList.add(temp);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }

    /**
     * 下拉刷新监听接口实现
     */
    @Override
    public void onRefresh() {
        refreshContent();
    }

    @Override
    public void onItemClick(View v) {
        startActivity(new Intent(getActivity(), ClassDetailActivity.class));
    }
}
