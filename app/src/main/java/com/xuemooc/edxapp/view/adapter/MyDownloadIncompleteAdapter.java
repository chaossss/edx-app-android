package com.xuemooc.edxapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.subview.MyDownloadIncompleteHolder;
import com.xuemooc.edxapp.view.subview.MyDownloadIncompleteItemHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaossss on 2015/10/22.
 */
public class MyDownloadIncompleteAdapter extends BaseExpandableListAdapter {
    private List<String> incompleteTasksName;
    private Map<String, List<String>> incompleteTasksInfo;

    public MyDownloadIncompleteAdapter() {
        incompleteTasksInfo = new HashMap<>();
        incompleteTasksName = new ArrayList<>();
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
            myDownloadIncompleteItemHolder = new MyDownloadIncompleteItemHolder(convertView);
            convertView.setTag(myDownloadIncompleteItemHolder);
        } else {
            myDownloadIncompleteItemHolder = (MyDownloadIncompleteItemHolder)convertView.getTag();
        }

        myDownloadIncompleteItemHolder.setItemName(incompleteTasksInfo.get(incompleteTasksName.get(groupPosition)).get(childPosition));
        myDownloadIncompleteItemHolder.setDownloadSum("");
        myDownloadIncompleteItemHolder.setDownloadPercent("");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
