package com.xuemooc.edxapp.view.adapter;

import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaos.imageloader.core.ImageLoader;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.utils.thread.LoadImageTask;
import com.xuemooc.edxapp.utils.util.MessageConst;
import com.xuemooc.edxapp.view.subview.ClassDetailInfoHolder;

/**
 * Created by chaossss on 2015/10/9.
 */
public class ClassDetailInfoAdapter extends RecyclerView.Adapter<ClassDetailInfoHolder> implements IWebMessage {
    private Bitmap teacherImg;

    public ClassDetailInfoAdapter() {
        Message msg = Message.obtain();
        msg.what = MessageConst.CLASS_DETAIL_TEACHER_IMG;
        msg.obj = "http://img.my.csdn.net/uploads/201504/13/1428914360_7495.jpg";
        ImageLoader.getImageLoader().load(new LoadImageTask(msg, this));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public ClassDetailInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_detail_info, parent, false);
        return new ClassDetailInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassDetailInfoHolder holder, int position) {
        holder.setTeacherImg(teacherImg);
    }

    @Override
    public void sendRequest(Message msg) {

    }

    @Override
    public void onMessageResponse(Message msg) {
        if(msg.what == MessageConst.CLASS_DETAIL_TEACHER_IMG){
            Bitmap bitmap = (Bitmap) msg.obj;
            teacherImg = bitmap;
            this.notifyDataSetChanged();
        }
    }
}
