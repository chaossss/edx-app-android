package com.xuemooc.edxapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.subview.MyDownloadIncompleteHolder;

import java.util.List;

/**
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteAdapter extends RecyclerView.Adapter<MyDownloadIncompleteHolder> {
    private List<List<String>> incompleteTaskList;

    public MyDownloadIncompleteAdapter(List<List<String>> incompleteTaskList) {
        this.incompleteTaskList = incompleteTaskList;
    }

    @Override
    public int getItemCount() {
        return incompleteTaskList.size();
    }

    @Override
    public MyDownloadIncompleteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_download_incomplete, parent, false);

        return new MyDownloadIncompleteHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyDownloadIncompleteHolder holder, int position) {
        holder.setIncompleteItemList(incompleteTaskList.get(position));
        this.notifyDataSetChanged();
    }
}
