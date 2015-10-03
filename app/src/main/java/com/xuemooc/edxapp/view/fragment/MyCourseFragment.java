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

    private String[] images = {
            "http://img.my.csdn.net/uploads/201509/29/1443486720_3622.jpg",
            "http://img.my.csdn.net/uploads/201508/28/1440748287_5111.JPG",
            "http://img.my.csdn.net/uploads/201508/28/1440748262_7910.JPG",
            "http://img.my.csdn.net/uploads/201506/05/1433498900_1272.jpg",
            "http://img.my.csdn.net/uploads/201506/05/1433497742_3244.jpg",

            "http://img.my.csdn.net/uploads/201505/21/1432198963_2181.jpg",
            "http://img.my.csdn.net/uploads/201505/21/1432198863_3060.jpg",
            "http://img.my.csdn.net/uploads/201505/21/1432196208_2233.jpg",
            "http://img.my.csdn.net/uploads/201505/13/1431511575_9681.jpg",
            "http://img.my.csdn.net/uploads/201505/12/1431442732_8432.jpg",

            "http://img.my.csdn.net/uploads/201505/12/1431442731_8175.jpg",
            "http://img.my.csdn.net/uploads/201504/26/1430014189_8490.png",
            "http://img.my.csdn.net/uploads/201504/26/1430014189_2164.png",
            "http://img.my.csdn.net/uploads/201504/25/1429952476_7900.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429952287_7023.jpg",

            "http://img.my.csdn.net/uploads/201504/25/1429949329_3391.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429949236_2350.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429949102_5644.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429948986_4450.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429948882_7915.jpg",

            "http://img.my.csdn.net/uploads/201504/25/1429948699_7368.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429948573_7445.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429948562_2232.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429948487_1166.jpg",
            "http://img.my.csdn.net/uploads/201504/25/1429948464_2685.png",

            "http://img.my.csdn.net/uploads/201504/25/1429948415_2193.png",
            "http://img.my.csdn.net/uploads/201504/25/1429948243_6050.png",
            "http://img.my.csdn.net/uploads/201504/18/1429318506_9608.jpg",
            "http://img.my.csdn.net/uploads/201504/18/1429318506_3728.jpg",
            "http://img.my.csdn.net/uploads/201504/18/1429318505_9339.jpg",

            "http://img.my.csdn.net/uploads/201504/18/1429318505_8739.jpg",
            "http://img.my.csdn.net/uploads/201504/17/1429260515_6846.jpg",
            "http://img.my.csdn.net/uploads/201504/17/1429260514_6147.jpg",
            "http://img.my.csdn.net/uploads/201504/17/1429260514_5562.jpg",
            "http://img.my.csdn.net/uploads/201504/17/1429260514_8011.jpg",

            "http://img.my.csdn.net/uploads/201504/13/1428914371_1788.png",
            "http://img.my.csdn.net/uploads/201504/13/1428914371_2167.png",
            "http://img.my.csdn.net/uploads/201504/13/1428914360_7454.png",
            "http://img.my.csdn.net/uploads/201504/13/1428914360_6429.png",
            "http://img.my.csdn.net/uploads/201504/13/1428914360_7495.jpg",
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_course, container, false);

        myCourseListView = (RecyclerView) root.findViewById(R.id.my_course_list);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.my_course_swipe);
        myCourseListView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        for(int i = 0;i < 5;++i)
        {
            MyCourseModel temp = new MyCourseModel("课程" + i, "学校" + i, images[i], "更新信息" + i);
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
