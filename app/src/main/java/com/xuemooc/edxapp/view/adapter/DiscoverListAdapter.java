package com.xuemooc.edxapp.view.adapter;

import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.model.data.DiscoverCourseModel;
import com.xuemooc.edxapp.utils.loader.ImageLoader;
import com.xuemooc.edxapp.utils.util.MessageConst;
import com.xuemooc.edxapp.view.subview.DiscoverListHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发现课程列表的数据填充器
 * Created by chaossss on 2015/7/30.
 */
public class DiscoverListAdapter extends RecyclerView.Adapter<DiscoverListHolder> implements View.OnClickListener, IWebMessage{
    private OnItemClickListener listener;
    private final Map<String, Bitmap> imageMap = new HashMap<>();
    private List<DiscoverCourseModel> courseList = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(View v);
    }

    /**
     * @param courseList 发现课程列表的数据项
     */
    public DiscoverListAdapter(List<DiscoverCourseModel> courseList) {
        super();
        this.courseList = courseList;
        Message msg = Message.obtain();
        msg.what = MessageConst.DISCOVER_LIST_IMG;
        msg.obj = "http://img.my.csdn.net/uploads/201505/12/1431442732_8432.jpg";
        ImageLoader.getImageLoader(this).load(msg);
    }

    /**
     * 添加新增课程
     * @param course
     */
    public void addCourse(DiscoverCourseModel course){
        courseList.add(course);
    }

    @Override
    public DiscoverListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_list, parent, false);
        view.setOnClickListener(this);
        return new DiscoverListHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverListHolder holder, int position)
    {
        DiscoverCourseModel course = courseList.get(position);
        holder.setInfo(course.getCourseName(),course.getSchool(),course.getWatchNums(),course.getTime());
        holder.setCourseImage(imageMap.get("http://img.my.csdn.net/uploads/201505/12/1431442732_8432.jpg"));
    }

    @Override
    public int getItemCount()
    {
        return courseList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v);
    }

    @Override
    public void sendRequest(Message msg) {

    }

    @Override
    public void onMessageResponse(Message msg) {
        if(msg.what == MessageConst.DISCOVER_LIST_IMG){
            Bitmap bitmap = (Bitmap) msg.obj;
            imageMap.put("http://img.my.csdn.net/uploads/201505/12/1431442732_8432.jpg", bitmap);
            this.notifyDataSetChanged();
        }
    }
}
