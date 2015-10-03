package com.xuemooc.edxapp.view.adapter;

import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.model.data.MyCourseModel;
import com.xuemooc.edxapp.utils.loader.ImageLoader;
import com.xuemooc.edxapp.utils.util.MessageConst;
import com.xuemooc.edxapp.view.subview.MyCourseListHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter used to fill data into MyCourse page's list
 *
 * Created by chaossss on 2015/7/30.
 */
public class MyCourseListAdapter extends RecyclerView.Adapter<MyCourseListHolder> implements View.OnClickListener, IWebMessage {
    private OnItemClickListener listener;
    private List<MyCourseModel> courseList = new ArrayList<>();

    private final Map<String, Bitmap> imageMap = new HashMap<>();

    public MyCourseListAdapter(List<MyCourseModel> courseList) {
        super();
        this.courseList = courseList;

        for(int i = 0; i < courseList.size(); i++){
            Message msg = Message.obtain();
            msg.what = MessageConst.MY_COURSE_LIST_IMG;
            msg.obj = courseList.get(i).getImgUrl();
            ImageLoader.getImageLoader().load(msg, this);
        }
    }

    /**
     * Add course to course list
     *
     * @param course course wanted to add
     */
    public void addCourse(MyCourseModel course){
        courseList.add(course);
    }

    /**
     * Remove specified course if exists
     *
     * @param pos Index of the specified course in the list
     * @return the specified course if exists;null if it doesn't
     */
    public MyCourseModel removeCourse(int pos){
        return courseList.remove(pos);
    }

    @Override
    public MyCourseListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_course_list, parent, false);
        view.setOnClickListener(this);
        MyCourseListHolder holder = new MyCourseListHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyCourseListHolder holder, int position)
    {
        MyCourseModel course = courseList.get(position);
        holder.setInfo(course.getCourseName(),course.getSchool(),course.getUpdateInfo());

        holder.setImage(imageMap.get(courseList.get(position).getImgUrl()));
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
        if(msg.what == MessageConst.MY_COURSE_LIST_IMG){
            Bitmap bitmap = (Bitmap) msg.obj;
            imageMap.put((String)msg.getData().get("url"), bitmap);
            this.notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v);
    }
}
