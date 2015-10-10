package com.xuemooc.edxapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.subview.MyDownloadIncompleteItemHolder;

import java.util.List;

/**
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteItemAdapter extends RecyclerView.Adapter<MyDownloadIncompleteItemHolder> {
    private List<String> incompleteItemDatas;

    public MyDownloadIncompleteItemAdapter(List<String> incompleteItemDatas) {
        this.incompleteItemDatas = incompleteItemDatas;
    }

    @Override
    public int getItemCount() {
        return incompleteItemDatas.size();
    }

    @Override
    public MyDownloadIncompleteItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_download_incomplete_item, parent, false);

        return new MyDownloadIncompleteItemHolder(rootView);
    }

    /**
     * 记得有一个是课程标题，剩下的才是下载任务！！！！
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyDownloadIncompleteItemHolder holder, int position) {

    }
}
