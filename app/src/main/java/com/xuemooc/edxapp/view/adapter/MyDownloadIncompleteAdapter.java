package com.xuemooc.edxapp.view.adapter;

import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.downloadlibrary.http.download.Downloader;
import com.chaos.downloadlibrary.http.download.FileDownloader;
import com.chaos.downloadlibrary.http.module.LoadInfo;
import com.chaos.downloadlibrary.util.state.DownloadConst;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.utils.interfaces.WebCommunication;
import com.xuemooc.edxapp.utils.thread.DownloadPretreatmentTask;
import com.xuemooc.edxapp.view.activity.TestActivity;
import com.xuemooc.edxapp.view.subview.MyDownloadIncompleteHolder;
import com.xuemooc.edxapp.view.subview.MyDownloadIncompleteItemHolder;
import com.xuemooc.edxapp.view.subview.TestHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaossss on 2015/10/22.
 */
public class MyDownloadIncompleteAdapter extends BaseExpandableListAdapter {
    private List<String> incompleteTasksName;
    private List<MyDownloadIncompleteItemHolder> subView;
    private Map<String, List<String>> incompleteTasksInfo;

    private View.OnClickListener onClickListener;

    public MyDownloadIncompleteAdapter(View.OnClickListener onClickListener) {
        subView = new ArrayList<>();
        incompleteTasksInfo = new HashMap<>();
        incompleteTasksName = new ArrayList<>();

        this.onClickListener = onClickListener;
    }

    public List<MyDownloadIncompleteItemHolder> getSubView() {
        return subView;
    }

    public void setIncompleteTasksInfo(Map<String, List<String>> incompleteTasksInfo) {
        this.incompleteTasksInfo = incompleteTasksInfo;
    }

    public void setIncompleteTasksName(List<String> incompleteTasksName) {
        this.incompleteTasksName = incompleteTasksName;
    }

    public Map<String, List<String>> getIncompleteTasksInfo() {
        return incompleteTasksInfo;
    }

    public List<String> getIncompleteTasksName() {
        return incompleteTasksName;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return incompleteTasksName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return incompleteTasksInfo.get(incompleteTasksName.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MyDownloadIncompleteHolder myDownloadIncompleteHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_download_incomplete, parent, false);
            myDownloadIncompleteHolder = new MyDownloadIncompleteHolder(convertView);
            convertView.setTag(myDownloadIncompleteHolder);
        } else {
            myDownloadIncompleteHolder = (MyDownloadIncompleteHolder) convertView.getTag();
        }

        myDownloadIncompleteHolder.setCourseName(incompleteTasksName.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MyDownloadIncompleteItemHolder myDownloadIncompleteItemHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_download_incomplete_item, parent, false);
            myDownloadIncompleteItemHolder = new MyDownloadIncompleteItemHolder(convertView, onClickListener);
            convertView.setTag(myDownloadIncompleteItemHolder);
        } else {
            myDownloadIncompleteItemHolder = (MyDownloadIncompleteItemHolder)convertView.getTag();
        }

        subView.add(myDownloadIncompleteItemHolder);
        myDownloadIncompleteItemHolder.setItemName(incompleteTasksInfo.get(incompleteTasksName.get(groupPosition)).get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
