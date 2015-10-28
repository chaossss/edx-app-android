package com.xuemooc.edxapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.subview.MyDownloadIncompleteItemHolder;
import com.xuemooc.edxapp.view.subview.TestHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by chaos on 2015/10/28.
 */
public class TestAdapter extends RecyclerView.Adapter<TestHolder> {
    private List<Map<String, String>> data;
    private View.OnClickListener onClickListener;

    public TestAdapter(List<Map<String, String>> data, View.OnClickListener onClickListener) {
        this.data=data;
        this.onClickListener = onClickListener;
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        TestHolder holder = new TestHolder(rootView, onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestHolder holder, int position) {
        holder.setResourceName(data.get(position).get("name"));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
